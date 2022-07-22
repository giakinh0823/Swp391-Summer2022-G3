var page = 1;
var searchSubject = "";
var subjectid;
const searchItemFilterSubjectQuestionImport = (element) => {
    $("#spin-search-subject-question-import").removeClass("hidden");
    $("#list-subject-question-import").html(``);
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
                                <input type="radio" value="${data.data[i].id}" ${subjectid == data.data[i].id ? "checked" : ""} onchange="selectSubjectQuestionImport(this)" name="subject" id="subject-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="subject-question-import-${data.data[i].id}" id="label-subject-question-import-${data.data[i].id}"  class="item-subject ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                    $("#spin-search-subject-question-import").addClass("hidden");
                } else {
                    $("#spin-search-subject-question-import").removeClass("hidden");
                }
                $("#list-subject-question-import").html(html);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }, 1000);
}


$('#list-subject-question-import').on('scroll', function () {
    if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
        $("#spin-search-subject-loadmore-question-import").removeClass("hidden");
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/search",
            data: { search: searchSubject, page: page + 1 },
            dataType: 'json',
            success: function (data) {
                var html = $("#list-subject-question-import").html();
                if (data != null && data.page != null) {
                    page = data.page;
                } else {
                    page = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    page++;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" onchange="selectSubjectQuestionImport(this)" name="subject" id="subject-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="subject-question-import-${data.data[i].id}" id="label-subject-question-import-${data.data[i].id}" class="item-subject ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                }
                $("#spin-search-subject-loadmore").addClass("hidden");
                $("#list-subject-question-import").html(html);
                if (subjectid != null) {
                    $(`#subject-question-import-${subjectid}`).prop("checked", true);
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
const selectSubjectQuestionImport = (element) => {
    $("#label-subject-question-import").removeClass("hidden");
    $("#label-subject-question-import").text($(`#label-subject-question-import-${$(element).val()}`).text());
    $("#lesson-content-question-import").addClass("hidden");
    $("#spin-search-lesson-question-import").removeClass("hidden");
    $("#dimension-content-question-import").addClass("hidden");
    $("#not-found-dimension-question-import").addClass("hidden");
    $("#not-found-lesson-question-import").addClass("hidden");
    $("#search-answer-content-question-import").removeClass("hidden");
    $("#list-lesson-question-import").removeClass("min-h-[180px] max-h-[200px]");
    $("#list-answer-question-import").html('');
    $("#spin-dimension-select-question-import").removeClass("hidden");
    $("#label-lesson-question-import").text("");
    $("#label-lesson-question-import").addClass("hidden");
    setTimeout(function () {
        listAnswers = [];
        subjectid = $(element).val();
        pageLesson = 1;
        pageAnser = 1;
        $("#create-answer-subject-question-import").val($(element).val());
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
            $("#lesson-content-question-import").removeClass("hidden");
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
                                                        <input type="radio" value="${data.data[i].id}" onchange="selectLessonQuestionImport(this)" name="lesson" id="lesson-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                                        <label for="lesson-question-import-${data.data[i].id}" id="label-lesson-question-import-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                                                    </div>`;
                }
                $("#list-lesson-question-import").addClass("min-h-[180px] max-h-[200px]");
                $("#not-found-lesson-question-import").addClass("hidden");
                $("#spin-search-lesson-question-import").addClass("hidden");
                $("#list-lesson-question-import").html(html);
            } else {
                $("#spin-search-lesson-question-import").removeClass("hidden");
                $("#list-lesson-question-import").html('');
                setTimeout(function () {
                    $.ajax({
                        method: "GET",
                        url: contextPath + "/admin/subject/lesson/search",
                        data: { subjectId: $(element).val() },
                        dataType: 'json',
                        success: function (data) {
                            $("#lesson-content-question-import").removeClass("hidden");
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
                                                        <input type="radio" value="${data.data[i].id}" onchange="selectLessonQuestionImport(this)" name="lesson" id="lesson-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                                        <label for="lesson-question-import-${data.data[i].id}" id="label-lesson-question-import-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                                                    </div>`;
                                }
                                $("#list-lesson-question-import").addClass("min-h-[180px] max-h-[200px]");
                                $("#not-found-lesson-question-import").addClass("hidden");
                                $("#spin-search-lesson-question-import").addClass("hidden");
                                $("#list-lesson-question-import").html(html);
                            } else {
                                $("#list-lesson-question-import").removeClass("min-h-[180px] max-h-[200px]");
                                $("#not-found-lesson-question-import").removeClass("hidden");
                                $("#spin-search-lesson-question-import").addClass("hidden");
                            }
                        }
                    });
                }, 500);
            }

            console.log(result);
            var htmlDimension = ``;
            $("#dimension-question-import").html(htmlDimension);
            if (result != null && result != null && result.length > 0) {
                for (var i = 0; i < result.length; i++) {
                    htmlDimension += ` <option value="${result[i].id}" >${result[i].name}</option>`;
                }
                $("#dimension-question-import").html(htmlDimension);
                $("#dimension-content-question-import").removeClass("hidden");
                $("#not-found-dimension-question-import").addClass("hidden");
                $("#spin-dimension-select-question-import").addClass("hidden");
            } else {
                $("#spin-dimension-select-question-import").removeClass("hidden");
                setTimeout(function () {
                    $.ajax({
                        method: "GET",
                        url: contextPath + "/admin/subject/dimension/search",
                        data: { subjectId: $(element).val() },
                        dataType: 'json',
                        success: function (result) {
                            var htmlDimension = ``;
                            $("#dimension-question-import").html(htmlDimension);
                            if (result != null && result != null && result.length > 0) {
                                for (var i = 0; i < result.length; i++) {
                                    htmlDimension += ` <option value="${result[i].id}" >${result[i].name}</option>`;
                                }
                                $("#dimension-question-import").html(htmlDimension);
                                $("#dimension-content-question-import").removeClass("hidden");
                                $("#not-found-dimension-question-import").addClass("hidden");
                                $("#spin-dimension-select-question-import").addClass("hidden");
                            } else {
                                $("#not-found-dimension-question-import").removeClass("hidden");
                                $("#dimension-content-question-import").addClass("hidden");
                                $("#spin-dimension-select-question-import").addClass("hidden");
                            }
                        }
                    });
                }, 1000);
            }
        }
        );
    }, 500)
}

$('#list-lesson-question-import').on('scroll', function () {
    if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
        $("#spin-search-lesson-loadmore-question-import").removeClass("hidden");
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/subject/lesson/search",
            data: { search: searchLesson, page: pageLesson + 1, subjectId: subjectid },
            dataType: 'json',
            success: function (data) {
                var html = $("#list-lesson-question-import").html();
                if (data != null && data.page != null) {
                    page = data.page;
                } else {
                    page = 1;
                }
                if (data != null && data.data != null && data.data.length > 0) {
                    ++pageLesson;
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<div class="flex items-center mb-2">
                                <input type="radio" value="${data.data[i].id}" onchange="selectLessonQuestionImport(this)" name="lesson" id="lesson-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="lesson-question-import-${data.data[i].id}" id="label-lesson-question-import-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                }
                $("#spin-search-lesson-loadmore-question-import").addClass("hidden");
                $("#list-lesson-question-import").html(html);
                if (lessonId != null) {
                    $(`#lesson-question-import-${lessonId}`).prop("checked", true);
                }
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
})


const searchItemFilterLessonQuestionImport = (element) => {
    $("#spin-search-lesson-question-import").removeClass("hidden");
    $("#list-lesson-question-import").html(``);
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
                                <input type="radio" value="${data.data[i].id}" ${lessonId == data.data[i].id ? "checked" : ""} onchange="selectLessonQuestionImport(this)" name="lesson" id="lesson-question-import-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="lesson-question-import-${data.data[i].id}"  id="label-lesson-question-import-${data.data[i].id}"  class="item-lesson ml-2 text-sm font-medium text-gray-900">${data.data[i].name}</label>
                            </div>`;
                    }
                    $("#spin-search-lesson-question-import").addClass("hidden");
                } else {
                    $("#spin-search-lesson-question-import").removeClass("hidden");
                }
                $("#list-lesson-question-import").html(html);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }, 1000);
}


const selectLessonQuestionImport = (element) => {
    lessonId = $(element).val();
    $("#label-lesson-question-import").text($(`#label-lesson-question-import-${$(element).val()}`).text());
    $("#label-lesson-question-import").removeClass("hidden");
}


var ExcelToJSON = function() {
    this.parseExcel = function(file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            var data = e.target.result;
            var workbook = XLSX.read(data, {
                type: 'binary'
            });
            workbook.SheetNames.forEach(function(sheetName) {
            // Here is your object
            var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
            var json_object = JSON.stringify(XL_row_object);
                console.log(json_object);
            })
        
        };
        reader.onerror = function(ex) {
            console.log(ex);
        };

       reader.readAsBinaryString(file);
    };
};

$("#file_excel").on("change", filePicked);

var listQuestions = [];

function filePicked(oEvent) {
    $("#content-question").addClass("hidden");
    // Get The File From The Input
    var oFile = oEvent.target.files[0];
    var sFilename = oFile.name;
    // Create A File Reader HTML5
    var reader = new FileReader();

    // Ready The Event For When A File Gets Selected
    reader.onload = function(e) {
        var data = e.target.result;
        var cfb = XLSX.read(data, {type: 'binary', raw: true, cellText: true});
        console.log(cfb)
        cfb.SheetNames.forEach(function(sheetName) {
            // Obtain The Current Row As CSV
            var sCSV = XLS.utils.make_csv(cfb.Sheets[sheetName]);   
            var oJS = XLS.utils.sheet_to_json(cfb.Sheets[sheetName]);   

//            $("#file_output").html(sCSV);
            console.log(oJS);
            listQuestions = oJS;
            var html = ``;
            listQuestions.forEach(item => {
                var answers = [];
                if(item["Answer"]){
                    answers= JSON.parse(item["Answer"]);
                }
                var corrects = [];
                if(item["Correct"]){
                   corrects = JSON.parse(item["Correct"]);
                }
                const question = item["Question"];
                const explain = item["Explain"];
                const level = item["Level"];
                const number = item["Number"];
                html += `<tr class="bg-white border-b" id="item-question-${number}">
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        ${number}
                                    </td>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        <p class="min-w-[200px]">
                                            ${question ? question : ""}
                                        </p>
                                    </td>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        ${level}
                                    </td>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        <p class="min-w-[200px]">
                                            ${explain ? explain : ""}
                                        </p>
                                    </td>
                                    <td class="px-6 py-4 font-medium text-gray-900 min-w-[250px]">
                                        ${answers.map((item,index)=>{
                                            if(corrects.indexOf(index+1)>=0){
                                                return `<span class="text-yellow-400">${item}</span>`
                                            }else{
                                                return item;
                                            }
                                        }).join(` <span class="text-red-500"> | </span> `)}
                                    </td>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        <span id="message-question-status-${number}"></span>
                                    </td>
                                    <td class="px-6 py-4 text-right">
                                                <button type="button" onclick="deleteQuestion(${number})"
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="currentColor"
                                                         viewBox="0 0 20 20"
                                                         xmlns="http://www.w3.org/2000/svg">
                                                    <path fill-rule="evenodd"
                                                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                                          clip-rule="evenodd"></path>
                                                    </svg>
                                                </button>
                                    </td>
                                </tr>`;
            });
            $("#list-question").html(html);
            $("#content-question").removeClass("hidden");
        });
    };

    // Tell JS To Start Reading The File.. You could delay this if desired
    reader.readAsBinaryString(oFile);
}

const deleteQuestion = (index) => {
    listQuestions = listQuestions.filter(item => item["Number"] !=index);
    $("#item-question-"+index).remove();
}



$("#form-question").on("submit", function (event) {
    event.preventDefault();
    $("#create-question-error").addClass("hidden");
    $("#button-question-model-spinner").click();
    
    var requests = [];
    
    listQuestions.forEach(item => {
        var answers = [];
        if(item["Answer"]){
             answers= JSON.parse(item["Answer"]);
        }
        var corrects = [];
        if(item["Correct"]){
            corrects = JSON.parse(item["Correct"]);
        }
        const question = item["Question"];
        const explain = item["Explain"];
        const level = item["Level"];
        const number = item["Number"];
        var formData = new FormData(this);
        
        for (var i = 0; i < answers.length; i++) {
            formData.append("answer", answers[i]);
        }
        
        for (var i = 0; i < corrects.length; i++) {
            formData.append("answer-result", corrects[i]);
        }
        
        if(level && level!=""){
             formData.append("level", level);
        }
        if(explain && explain!=""){
            formData.append("explain", explain);
        }
        if(question && question!=""){
            formData.append("question", question);
        }
        
         
        requests.push($.ajax({
            method: 'POST',
            url: contextPath + '/admin/question/import',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                $("#item-question-"+number).addClass("bg-green-400");
                $("#message-question-status-"+number).text("Create success");
            },
            error: function (error) {
                $("#item-question-"+number).addClass("bg-red-400");
                if(error.responseJSON.message){
                    $("#message-question-status-"+number).text(error.responseJSON.message);
                }else{
                    $("#message-question-status-"+number).text("Can't not create! some thing error");
                }
            }
        }));
    });
    
    Promise.all(requests).then(() => { 
         $("#button-question-model-spinner").click();
         window.location.href = contextPath + "/admin/question"
    }).catch(() => {
         $("#button-question-model-spinner").click();
    });
    
})

const clearForm =() => {
    $("#list-question").html(``);
    listQuestions = [];
}