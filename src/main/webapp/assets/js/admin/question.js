/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

//
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

var pageLesson = 1;
var searchLesson = "";
var lessonId;
const selectSubject = (element) => {
    $("#label-subject").removeClass("hidden");
    $("#label-subject").text($(`#label-subject-${$(element).val()}`).text());
    $("#lesson-content").addClass("hidden");
    $("#spin-search-lesson").removeClass("hidden");
    $("#dimension-content").addClass("hidden");
    $("#not-found-dimension").addClass("hidden");
    $("#not-found-lesson").addClass("hidden");
    $("#search-answer-content").removeClass("hidden");
    $("#list-lesson").removeClass("min-h-[180px] max-h-[200px]");
    $("#list-answer").html('');
    $("#spin-dimension-select").removeClass("hidden");
    $("#label-lesson").text("");
    $("#label-lesson").addClass("hidden");
    setTimeout(function () {
        listAnswers = [];
        subjectid = $(element).val();
        pageLesson = 1;
        pageAnser = 1;
        $("#create-answer-subject").val($(element).val());
        $.when(
            $.ajax({
                method: "GET",
                url: contextPath + "/admin/subject/lesson/search",
                data: { subjectId: $(element).val() },
                dataType: 'json'
            }),
            $.ajax({
                method: "GET",
                url: contextPath + "/admin/subject/dimension/search",
                data: { subjectId: $(element).val() },
                dataType: 'json',
            }),
        ).done(function (data1, data2) {
            console.log(data1);
            const data = data1[0];
            const result = data2[0];
            $("#lesson-content").removeClass("hidden");
            var html = ``;
            if (data != null && data.page != null) {
                pageLesson = data.page;
            } else {
                pageLesson = 1;
            }
            console.log(data);
            if (data != null && data.data != null && data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    html += `<div class="flex items-center mb-2">
                                                        <input type="checkbox" value="${data.data[i].id}" onchange="selectLesson(this)" name="lesson" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                                        <label for="lesson-${data.data[i].id}" id="label-lesson-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                                                    </div>`;
                }
                $("#list-lesson").addClass("min-h-[180px] max-h-[200px]");
                $("#not-found-lesson").addClass("hidden");
                $("#spin-search-lesson").addClass("hidden");
                $("#list-lesson").html(html);
            } else {
                $("#spin-search-lesson").removeClass("hidden");
                $("#list-lesson").html('');
                setTimeout(function () {
                    $.ajax({
                        method: "GET",
                        url: contextPath + "/admin/subject/lesson/search",
                        data: { subjectId: $(element).val() },
                        dataType: 'json',
                        success: function (data) {
                            $("#lesson-content").removeClass("hidden");
                            var html = ``;
                            if (data != null && data.page != null) {
                                pageLesson = data.page;
                            } else {
                                pageLesson = 1;
                            }
                            console.log(data);
                            if (data != null && data?.data != null && data?.data?.length > 0) {
                                for (var i = 0; i < data.data.length; i++) {
                                    html += `<div class="flex items-center mb-2">
                                                        <input type="checkbox" value="${data.data[i].id}" name="lesson" onchange="selectLesson(this)" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                                        <label for="lesson-${data.data[i].id}" id="label-lesson-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                                                    </div>`;
                                }
                                $("#list-lesson").addClass("min-h-[180px] max-h-[200px]");
                                $("#not-found-lesson").addClass("hidden");
                                $("#spin-search-lesson").addClass("hidden");
                                $("#list-lesson").html(html);
                            } else {
                                $("#list-lesson").removeClass("min-h-[180px] max-h-[200px]");
                                $("#not-found-lesson").removeClass("hidden");
                                $("#spin-search-lesson").addClass("hidden");
                            }
                        }
                    });
                }, 500);
            }

            console.log(result);
            var htmlDimension = ``;
            $("#dimension-list").html(htmlDimension);
            if (result != null && result != null && result.length > 0) {
                for (var i = 0; i < result.length; i++) {
                    htmlDimension += `<div class="flex items-center mb-2">
                                            <input type="checkbox" value="${result[i].id}" name="dimennsion"  id="dimennsion-${result[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                            <label for="dimennsion-${result[i].id}" id="label-dimennsion-${result[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${result[i].name}</label>
                                        </div>`;
                }
                $("#dimension-list").html(htmlDimension);
                $("#dimension-content").removeClass("hidden");
                $("#not-found-dimension").addClass("hidden");
                $("#spin-dimension-select").addClass("hidden");
            } else {
                $("#spin-dimension-select").removeClass("hidden");
                setTimeout(function () {
                    $.ajax({
                        method: "GET",
                        url: contextPath + "/admin/subject/dimension/search",
                        data: { subjectId: $(element).val() },
                        dataType: 'json',
                        success: function (result) {
                            var htmlDimension = ``;
                            $("#dimension-list").html(htmlDimension);
                            if (result != null && result != null && result.length > 0) {
                                for (var i = 0; i < result.length; i++) {
                                    htmlDimension += `<div class="flex items-center mb-2">
                                            <input type="checkbox" value="${result[i].id}" name="dimennsion"  id="dimennsion-${result[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                            <label for="dimennsion-${result[i].id}" id="label-dimennsion-${result[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${result[i].name}</label>
                                        </div>`;
                                }
                                $("#dimension-list").html(htmlDimension);
                                $("#dimension-content").removeClass("hidden");
                                $("#not-found-dimension").addClass("hidden");
                                $("#spin-dimension-select").addClass("hidden");
                            } else {
                                $("#not-found-dimension").removeClass("hidden");
                                $("#dimension-content").addClass("hidden");
                                $("#spin-dimension-select").addClass("hidden");
                            }
                        }
                    });
                }, 1000);
            }
        }
        );
    }, 500)
}

$('#list-lesson').on('scroll', function () {
    if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
        $("#spin-search-lesson-loadmore").removeClass("hidden");
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/lesson/search",
            data: { search: searchLesson, page: pageLesson + 1, subjectId: subjectid },
            dataType: 'json',
            success: function (data) {
                var html = $("#list-lesson").html();
                if (data != null && data.page != null) {
                    page = data.page;
                } else {
                    page = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    ++pageLesson;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" onchange="selectLesson(this)" name="lesson" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="lesson-${data.data[i].id}" id="label-lesson-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                }
                $("#spin-search-lesson-loadmore").addClass("hidden");
                $("#list-lesson").html(html);
                if (lessonId != null) {
                    $(`#lesson-${lessonId}`).prop("checked", true);
                }
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
})


const searchItemFilterLesson = (element) => {
    $("#spin-search-lesson").removeClass("hidden");
    $("#list-lesson").html(``);
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/lesson/search",
            data: { search: $(element).val(), page: 1, subjectId: subjectid },
            dataType: 'json',
            success: function (data) {
                var html = ``;
                searchLesson = $(element).val() ? $(element).val() : "";
                if (data != null && data.page != null) {
                    pageLesson = data.page;
                } else {
                    pageLesson = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" ${lessonId == data.data[i].id ? "checked" : ""} onchange="selectLesson(this)" name="lesson" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="lesson-${data.data[i].id}"  id="label-lesson-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                    $("#spin-search-lesson").addClass("hidden");
                } else {
                    $("#spin-search-lesson").removeClass("hidden");
                }
                $("#list-lesson").html(html);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }, 1000);
}


const selectLesson = (element) => {
    lessonId = $(element).val();
    $("#label-lesson").text($(`#label-lesson-${$(element).val()}`).text());
    $("#label-lesson").removeClass("hidden");
}


const changeActiveQuestion = (element, url) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: url,
        data: {id: $(element).val(), status: $(element).is(":checked")},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text(message.code);
        },
        error: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
        }
    })
}