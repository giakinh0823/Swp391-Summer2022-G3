function dropdownfilter() {
    var x = document.getElementById("box-filter");
    var courseList = document.getElementById("box-course-list");
    if (x.style.display === "none") {
        courseList.classList.remove('col-span-12');
        courseList.classList.add('col-span-9');
        x.style.display = "block";
    } else {
        courseList.classList.remove('col-span-9');
        courseList.classList.add('col-span-12');
        x.style.display = "none";

    }
}

function showmorecategoryfilter() {
    var x = document.getElementById("box-filter-catory-item");
    if ($("#box-filter-catory-item").hasClass("max-h-[21rem]")) {
        x.classList.remove('max-h-[21rem]');
        x.classList.add('max-h-[42rem]');
    } else {
        x.classList.remove('max-h-[42rem]');
        x.classList.add('max-h-[21rem]');
    }
}

function showmoreauthorfilter() {
    var x = document.getElementById("box-filter-author-item");
    if ($("#box-filter-author-item").hasClass("max-h-[21rem]")) {
        x.classList.remove('max-h-[21rem]');
        x.classList.add('max-h-[42rem]');
    } else {
        x.classList.remove('max-h-[42rem]');
        x.classList.add('max-h-[21rem]');
    }
}


$("#search-category").keypress(function (e) {
    if (e.which == 13) {
        e.preventDefault();
    }
})

const openModalCheckout = (id) => {
    $("#checkout-subject-image").removeAttr("src");
    $("#checkout-subject-image-href").removeAttr("href");
    $("#checkout-subject-title-href").removeAttr("href");
    $("#checkout-subject-title").text("");
    $("#checkout-subject-description").text("");
    $("#checkout-subject").val("");
    $.ajax({
        method: "GET",
        url: contextPath + "/course/get",
        data: {id: id},
        dataType: 'json',
        success: function (data) {
            console.log(data);
            if(data.image.toString().indexOf('dummyimage.com')>=0){
                $("#checkout-subject-image").attr("src", data.image);
            }else{
                $("#checkout-subject-image").attr("src", contextPath + "/images/subject/" + data.image);
            }
            
            $("#checkout-subject-image-href").attr("href", contextPath + "/course/" + data.id);
            $("#checkout-subject-title-href").attr("href", contextPath + "/course/" + data.id);
            $("#checkout-subject-title").text(data.name);
            $("#checkout-subject-description").text(data.description);
            $("#checkout-subject").val(data.id);
            var html = ``;
            for (var i = 0; i < data.packegePrices.length; i++) {
                html += `<tr class="bg-white">
                                    <td class="py-2 font-medium">
                                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path></svg>
                                    </td>
                                    <td class="py-2 font-medium text-left">
                                        ${data.packegePrices[i].name}
                                    </td>
                                    <td class="px-2 py-2 font-medium line-through">
                                        ${data.packegePrices[i].list_price} $
                                    </td>
                                    <td class="px-2 py-2 font-medium">
                                        ${data.packegePrices[i].sale_price} $
                                    </td>
                                    <td class="px-2 py-2 font-medium">
                                        <div class="flex items-center">
                                            <input type="radio" value="${data.packegePrices[i].id}" name="price" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                        </div>
                                    </td>
                                </tr>`;
            }
            $("#checkout-body-price").html(html);
        }
    });
}



$("#form-checkout").on("submit", function (event) {
    event.preventDefault();
    $("#error-checkout").addClass("hidden");
    var form = new FormData(this);
    $.ajax({
        method: "POST",
        url: contextPath + "/course/checkout",
        data: $(this).serialize(),
        dataType: 'json',
        success: function (data) {
            window.location.href = contextPath + "/course/register";
        },
        error: function (error) {
            $("#error-checkout").removeClass("hidden");
            $("#error-message-checkout").text(error.responseJSON.message);
        }
    })
})
