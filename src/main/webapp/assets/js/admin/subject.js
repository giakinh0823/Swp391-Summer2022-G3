/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$("#search-category").keypress(function (e) {
    if (e.which == 13) {
        e.preventDefault();
    }
})

//$(function () {
//    $("#filter-drag-content").draggable({
//        cursor: "move",
//        start: function () {
//            $("#button-open-filter").removeClass("hidden");
//            $("#button-open-filter").removeClass("md:hidden");
//            $("#button-close-filter").removeClass("hidden");
//            $("#filter-drag-layout").addClass(`fixed top-[122px] right-[20px]`);
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


const changeActiveSubject = (element) => {
    $("#button-confirm-change-status").attr("onclick", `confirmActiveSubject(${$(element).val()},${$(element).is(":checked")})`);
    $(element).prop("checked", !$(element).is(":checked"));
    $("#button-close-change-status").click();
}

const confirmActiveSubject = (id, status) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/subject/status/change",
        data: {id: id, status: status},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text(message.code);
            $("#button-close-change-status").click();
            $("#toggle-subject-status-"+id).prop("checked", status);
        },
        error: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
            $("#button-close-change-status").click();
        }
    })
}

const changeFeatureSubject = (element) => {
    $("#button-confirm-change-status").attr("onclick", `confirmFeatureSubject(${$(element).val()},${$(element).is(":checked")})`);
    $(element).prop("checked", !$(element).is(":checked"));
    $("#button-close-change-status").click();
}


const confirmFeatureSubject = (id, status) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/subject/feature/change",
        data: {id: id, feature:status},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text(message.code);
            $("#button-close-change-status").click();
            $("#toggle-subject-featured-"+id).prop("checked", status);
        },
        error: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(message.responseJSON.message);
            $("#alert-message-code").text("Error!");
            $("#button-close-change-status").click();
        }
    });
}