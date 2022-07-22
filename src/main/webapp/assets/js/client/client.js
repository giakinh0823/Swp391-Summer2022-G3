const parseParams = (querystring) => {
    // parse query string
    const params = new URLSearchParams(querystring);
    const obj = {};
    // iterate over all keys
    for (const key of params.keys()) {
        if (params.getAll(key).length > 1) {
            obj[key] = params.getAll(key);
        } else {
            obj[key] = params.get(key);
        }
    }
    return obj;
};

const url_string = window.location.href;
const url = new URL(url_string);
const search = url.searchParams.get("q");
const paginationLinks = document.querySelectorAll(".page-link");
if (paginationLinks) {
    paginationLinks.forEach(item => {
        var search = window.location.search;
        const params = parseParams(search);
        const page = item.getAttribute("data");
        const {code, message, ...newParams} = params;
        newParams.page = page;
        const href = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
        item.setAttribute("href", "?" + href);
    })
}


const filterClickHandleColumn = (field, value) => {
     var search = window.location.search;
    const params = parseParams(search);
    const {code, message,page, ...newParams} = params;
    newParams[field] = value;
    const href = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
    window.location.search = href;
}



const searchItemFilter = (field, element) => {
    event.preventDefault();
    const val = $(element).val();
    var html = "";
    $(`.item-${field}`).each(function () {
        const text = $(this).text();
        if (text.toString().trim().toUpperCase().includes(val.toString().trim().toUpperCase())) {
            $(this).parent().removeClass("hidden");
        } else {
            $(this).parent().addClass("hidden");
        }
    });
}

const removeFilter = (field, value) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    if (typeof newParams[field] == 'string' || newParams[field] instanceof String) {
        delete newParams[field];
    }else{       
        newParams[field] = newParams[field]?.filter(item => item !=value);
    }
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}


const sortHandleColumn = (sort) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, ...newParams} = params;

    if (newParams.sort == undefined) {
        newParams.sort = [];
    } else if (typeof newParams.sort == 'string' || newParams.sort instanceof String) {
        newParams.sort = [newParams.sort];
    }

    const map = new Map();
    newParams.sort.forEach(item => {
        if (typeof item == 'string' || item instanceof String) {
            var items = item.split("_");
            if (items.length > 1) {
                map[items[0]] = items[1];
            }
        }
    })

    const regex = /^[a-zA-Z]+_(ASC|DESC)$/;
    if (regex.test(sort)) {
        var sorts = sort.split("_");
        if (sorts.length > 1) {
            map[sorts[0]] = sorts[1];
        }
    }

    newParams.sort = [];
    for (let key of Object.keys(map)) {
        const value = map[key];
        newParams.sort.push(key + "_" + value);
    }

    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}