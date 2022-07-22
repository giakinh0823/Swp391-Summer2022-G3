const showSliderbar = (element) => {
    if ($("#sliderar-admin").hasClass("hidden") && $("#sliderar-admin-layout").hasClass("hidden")) {
        $("#sliderar-admin").removeClass("hidden");
        $("#sliderar-admin-layout").removeClass("hidden");
        $(element).removeClass("lef-0");
        $(element).addClass("left-[200px]");
    } else {
        $("#sliderar-admin").addClass("hidden");
        $("#sliderar-admin-layout").addClass("hidden");
        $(element).removeClass("left-[200px]");
        $(element).addClass("lef-0");
    }
}



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

const sortHandle = (element) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, ...newParams} = params;
    newParams.sort = $(element).val();
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}

const filterStatus = (element) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    newParams.status = $(element).val();
    console.log(newParams);
    const href = decodeURIComponent(new URLSearchParams(newParams).toString());
    window.location.search = encodeURI(href);
}

const filterTypes = (element) => {
    var search = location.search.substring(1);
    const params = search ? JSON.parse('{"' + decodeURI(search).replace(/"/g, '\\"')
            .replace(/&/g, '","').replace(/=/g, '":"') + '"}') : {};
    const {code, message, page, ...newParams} = params;
    newParams.type = $(element).val();
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}

const filterGender = (element) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    newParams.gender = $(element).val();
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}



const filterRole = (element) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    newParams.role = $(element).val();
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}


const filterHandle = (field, element) => {
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    newParams[field] = $(element).val();
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}


const sortHandleColumn = (sort, element, idShow) => {
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

    $(element).addClass("hidden");
    $(`#${idShow}`).removeClass("hidden");
    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
}


const filterHandleColumn = (element) => {
    var field = $(element).attr("name");
    var search = window.location.search;
    const params = parseParams(search);
    const {code, message, page, ...newParams} = params;
    newParams[field] = [];

    $(`input[name='${field}']:checked`).each(function () {
        newParams[field].push($(this).val());
    })

    if ($(element).is(":checked")) {
        $(`input[parent='${$(element).val()}']`).each(function () {
            newParams[field].push($(this).val());
        });
    }else{
        $(`input[parent='${$(element).val()}']`).each(function () {
            newParams[field] = newParams[field].filter(item => item!= $(this).val());
        });
        newParams[field] = newParams[field].filter(item => item!= $(element).attr("parent"));
    }
    
    if($(element).attr("parent") &&
            $(`input[parent='${$(element).attr("parent")}']:checked`).length == $(`input[parent='${$(element).attr("parent")}']`).length ){
        newParams[field].push($(element).attr("parent"));
    }

    let sets = new Set(newParams[field]);
    newParams[field] = Array.from(sets);

    window.location.search = encodeURI(decodeURIComponent($.param(newParams)).toString().replace(/[[]]/ig, ""));
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


