var page = 1;
var searchSubject = "";

const searchItemFilterSubject = (element) => {
    $("#spin-search-subject").removeClass("hidden");
    $("#list-subject").html(``);
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/search",
            data: {search: $(element).val(), page: 1},
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
            data: {search: searchSubject, page: page + 1},
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


const selectSubject = (element) => {
    $("#error-setting").addClass("hidden");
    $("#error-setting-dimension").addClass("hidden");
    subjectid = $(element).val();
    $("#selected-dimension").addClass("hidden");
    $(".dimension").each(function () {
        $(this).html("");
    });
    $("input[name='type-dimension']").prop("checked", false);
    listDimensions = [];
    $("#selected-dimension").html(`<div class="flex mb-3">
                                <select id="dimension-selected"
                                        class="dimension bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                </select>
                                <input type="number" min="1" id="new_dimenion" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                                <button type="button" class="ml-2" onclick="saveDimension($('#new_dimenion').val(),$('#dimension-selected').val(), $('#dimension-selected option:selected').text())">
                                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>                                
                                </button>
                            </div>`);
}



var dimensions = []
const getDimension = (id) => {
    $("#error-setting").addClass("hidden");
    $("#error-setting-dimension").addClass("hidden");
    $("#selected-dimension").addClass("hidden");
    if (id && subjectid) {
        $("#spin-select-type-dimension").removeClass("hidden");
        setTimeout(function () {
            $.ajax({
                method: "GET",
                url: contextPath + "/admin/subject/dimension/search",
                data: {subjectId: subjectid, typeId: id},
                success: function (data) {
                    console.log(data);
                    dimensions = data;
                    if (data != null && data.length > 0) {
                        $(".dimension").each(function () {
                            html = "";
                            for (var i = 0; i < data.length; i++) {
                                html += `<option value="${data[i].id}">${data[i].name}</option>`;
                            }
                            $(this).html(html);
                        });
                        $("#selected-dimension").removeClass("hidden");
                    } else {
                        $("#dimension").html("");
                        $("#error-setting-dimension").removeClass("hidden");
                        $("#selected-dimension").addClass("hidden");
                    }
                    $("#spin-select-type-dimension").addClass("hidden");
                },
                error: function (error) {
                    $("#error-setting").removeClass("hidden");
                    $("#selected-dimension").addClass("hidden");
                    if (error.responseJSON) {
                        $("#message-error-setting").text(error.responseJSON.message);
                    } else {
                        $("#message-error-setting").text("Some thing error!");
                    }
                    $("#spin-select-type-dimension").addClass("hidden");
                }
            });
        }, 500);
    } else {
        $("#error-setting").removeClass("hidden");
        $("#message-error-setting").text("Please choose subject");
    }
};



const saveDimension = (number, id, text) => {
    $("#error-setting").addClass("hidden");
    console.log(number, id, text);
    const result = listDimensions.filter(item => item.id == id);
    if (result.length > 0 && number > 0) {
        result[0].id = id;
        result[0].name = text;
        result[0].question = number;
    } else if (text != null && id != null && number != null && number > 0) {
        listDimensions.push({id: id, name: text, question: number});
    } else {
        $("#error-setting").removeClass("hidden");
        $("#message-error-setting").text("Number must be > 0!");
    }
    var html = '';
    for (var i = 0; i < listDimensions.length; i++) {
        html += `<div class="flex mb-3">
                        <input type="hidden" min="1" name="dimension" value="${listDimensions[i].id}" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <input type="text" disabled value="${listDimensions[i].name}" class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <input type="number" min="1" name="number_question" value="${listDimensions[i].question}" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <button type="button" class="ml-2" onclick="deleteDimension(${listDimensions[i].id})">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                        </button>
                   </div>`
    }
    html += ` <div class="flex mb-3">
                            <select id="dimension-selected"
                                class="dimension bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            `;
    for (var j = 0; j < dimensions.length; j++) {
        html += `<option value="${dimensions[j].id}"  ${dimensions[j].id == id ? 'selected' : ''}>${dimensions[j].name}</option>`;
    }
    html += `</select>
                            <input type="number" min="1" id="new_dimenion" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                            <button type="button" class="ml-2" onclick="saveDimension($('#new_dimenion').val(),$('#dimension-selected').val(), $('#dimension-selected option:selected').text())">
                                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>                                
                            </button>
                     </div>`;
    $("#selected-dimension").html(html);
}


const deleteDimension = (id) => {
    listDimensions= listDimensions.filter(item => item.id != id);
    var html = '';
    for (var i = 0; i < listDimensions.length; i++) {
        html += `<div class="flex mb-3">
                        <input type="hidden" min="1" name="dimension" value="${listDimensions[i].id}" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <input type="text" disabled value="${listDimensions[i].name}" class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <input type="number" min="1" name="number_question" value="${listDimensions[i].question}" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <button type="button" class="ml-2" onclick="deleteDimension(${listDimensions[i].id})">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                        </button>
                   </div>`
    }
    html += ` <div class="flex mb-3">
                            <select id="dimension-selected"
                                class="dimension bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            `;
    for (var j = 0; j < dimensions.length; j++) {
        html += `<option value="${dimensions[j].id}"  ${dimensions[j].id == id ? 'selected' : ''}>${dimensions[j].name}</option>`;
    }
    html += `</select>
                            <input type="number" min="1" id="new_dimenion" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                            <button type="button" class="ml-2" onclick="saveDimension($('#new_dimenion').val(),$('#dimension-selected').val(), $('#dimension-selected option:selected').text())">
                                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>                                
                            </button>
                     </div>`;
    $("#selected-dimension").html(html);
}

$("#form-quizzes").on("submit", function(event){
    $("#error-setting").addClass("hidden");
    $("#error-overview").addClass("hidden");
    event.preventDefault();
    const from = $(this);
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/quizzes/edit/"+quizzesid,
        data: from.serialize(),
        dataType: 'json',
        success: function(data){
            console.log(data);
            window.location.href = contextPath + "/admin/quizzes";
        },
        error: function(error){
            $("#error-setting").removeClass("hidden");
            $("#error-overview").removeClass("hidden");
            if(error.responseJSON){
                $("#message-error-setting").text(error.responseJSON.message);
                $("#message-error-overview").text(error.responseJSON.message);
            }else{
                $("#message-error-setting").text("Some thing error!");
                $("#message-error-overview").text("Some thing error!");
            }
        }
    })
})