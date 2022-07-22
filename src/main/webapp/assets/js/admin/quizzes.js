/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


//$(function () {
//    $("#filter-drag-content").draggable({
//        cursor: "move",
//        start: function () {
//            $("#button-open-filter").removeClass("hidden");
//            $("#button-open-filter").removeClass("md:hidden");
//            $("#button-close-filter").removeClass("hidden");
//            $("#filter-drag-layout").addClass(`fixed top-[100px] right-[20px]`);
//        },
//    });
//});
//

const showFilterbar = (element) => {
        $("#button-open-filter").removeClass("md:hidden");
    if ($("#filter-drag-layout").hasClass("hidden")) {
        $("#button-close-filter").removeClass("hidden");
        $("#filter-drag-layout").removeClass("hidden");
        $("#filter-drag-content").removeClass("hidden");
        $("#filter-drag-layout").addClass(`fixed top-[20px] right-[10px]`);
        $("#filter-drag-content").css({'top': -10, 'left' : 0});
    } else {
        $("#button-close-filter").addClass("hidden");
        $("#filter-drag-layout").addClass("hidden");
        $("#filter-drag-content").addClass("hidden");
    }
}

const closeFilter = () => {
    $("#button-open-filter").removeClass("md:hidden");
    $("#button-open-filter").removeClass("hidden");
    $("#filter-drag-content").addClass("hidden");
    $("#button-close-layout").addClass("hidden");
}


var page = 1;
var searchSubject = "";
var subjectid;
const searchItemFilterSubject = (element) => {
    $("#spin-search-subject").removeClass("hidden");
    $("#list-subject").html(``);
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/search",
            data: { search: $(element).val(), page: 1 },
            dataType: 'json',
            success: function (data) {
                var html = ``;
                searchSubject = $(element).val();
                if (data != null && data.page != null) {
                    page = data.page;
                } else {
                    page = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" ${subjectid == data.data[i].id ? "checked" : ""} onchange="selectSubject(this)" name="subject" id="subject-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="subject-${data.data[i].id}" id="label-subject-${data.data[i].id}"  class="item-subject ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                    $("#spin-search-subject").addClass("hidden");
                } else {
                    $("#spin-search-subject").removeClass("hidden");
                }
                $("#list-subject").html(html);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }, 1000);
}


$('#list-subject').on('scroll', function () {
    if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
        $("#spin-search-subject-loadmore").removeClass("hidden");
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/search",
            data: { search: searchSubject, page: page + 1 },
            dataType: 'json',
            success: function (data) {
                var html = $("#list-subject").html();
                if (data != null && data.page != null) {
                    page = data.page;
                } else {
                    page = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    page++;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" onchange="selectSubject(this)" name="subject" id="subject-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="subject-${data.data[i].id}" id="label-subject-${data.data[i].id}" class="item-subject ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                }
                $("#spin-search-subject-loadmore").addClass("hidden");
                $("#list-subject").html(html);
                if (subjectid != null) {
                    $(`#subject-${subjectid}`).prop("checked", true);
                }
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
})