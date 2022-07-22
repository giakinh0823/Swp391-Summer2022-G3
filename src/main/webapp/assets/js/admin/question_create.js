var editorcfg = {}
var listImages = []
var listDash = []

editorcfg.editorResizeMode = "height";

editorcfg.file_upload_handler = function (file, callback, optionalIndex, optionalFiles) {
    var uploadhandlerpath = contextPath + "/admin/question/media/uploads";

    console.log("upload", file, "to", uploadhandlerpath)

    function append(parent, tagname, csstext) {
        var tag = parent.ownerDocument.createElement(tagname);
        if (csstext)
            tag.style.cssText = csstext;
        parent.appendChild(tag);
        return tag;
    }

    var uploadcancelled = false;

    var dialogouter = append(document.body, "div", "display:flex;align-items:center;justify-content:center;z-index:999999;position:fixed;left:0px;top:0px;width:100%;height:100%;background-color:rgba(128,128,128,0.5)");
    var dialoginner = append(dialogouter, "div", "background-color:white;border:solid 1px gray;border-radius:15px;padding:15px;min-width:200px;box-shadow:2px 2px 6px #7777");

    var line1 = append(dialoginner, "div", "text-align:center;font-size:1.2em;margin:0.5em;");
    line1.innerText = "Uploading...";

    var totalsize = file.size;
    var sentsize = 0;

    if (optionalFiles && optionalFiles.length > 1) {
        totalsize = 0;
        for (var i = 0; i < optionalFiles.length; i++) {
            totalsize += optionalFiles[i].size;
            if (i < optionalIndex)
                sentsize = totalsize;
        }
        console.log(totalsize, optionalIndex, optionalFiles)
        line1.innerText = "Uploading..." + (optionalIndex + 1) + "/" + optionalFiles.length;
    }

    var line2 = append(dialoginner, "div", "text-align:center;font-size:1.0em;margin:0.5em;");
    line2.innerText = "0%";

    var progressbar = append(dialoginner, "div", "border:solid 1px gray;margin:0.5em;");
    var progressbg = append(progressbar, "div", "height:12px");

    var line3 = append(dialoginner, "div", "text-align:center;font-size:1.0em;margin:0.5em;");
    var btn = append(line3, "button");
    btn.className = "btn btn-primary";
    btn.innerText = "cancel";
    btn.onclick = function () {
        uploadcancelled = true;
        xh.abort();
    }

    var xh = new XMLHttpRequest();
    xh.open("POST", uploadhandlerpath + "?name=" + encodeURIComponent(file.name) + "&type=" + encodeURIComponent(file.type) + "&size=" + file.size, true);
    xh.onload = xh.onabort = xh.onerror = function (pe) {
        console.log(pe);
        console.log(xh);
        dialogouter.parentNode.removeChild(dialogouter);
        if (pe.type == "load") {
            if (xh.status != 200) {
                console.log("uploaderror", pe);
                if (xh.responseText.startsWith("ERROR:")) {
                    callback(null, "http-error-" + xh.responseText.substring(6));
                } else {
                    callback(null, "http-error-" + xh.status);
                }
            } else if (xh.responseText.startsWith("READY:")) {
                console.log("File uploaded to " + xh.responseText.substring(6));
                callback(xh.responseText.substring(6));
            } else {
                callback(null, "http-error-" + xh.responseText);
            }
        } else if (uploadcancelled) {
            console.log("uploadcancelled", pe);
            callback(null, "cancelled");
        } else {
            console.log("uploaderror", pe);
            callback(null, pe.type);
        }
    }
    xh.upload.onprogress = function (pe) {
        console.log(pe);
        //pe.total
        var percent = Math.floor(100 * (sentsize + pe.loaded) / totalsize);
        line2.innerText = percent + "%";

        progressbg.style.cssText = "background-color:green;width:" + (percent * progressbar.offsetWidth / 100) + "px;height:12px;";
    }
    var formData = new FormData();
    formData.append("file", file);
    xh.send(formData);
}


editorcfg.tabSpaces = "&nbsp;&nbsp;&nbsp;";
var editor = new RichTextEditor("#content", editorcfg);
var explain = new RichTextEditor("#explain", editorcfg);


editor.attachEvent("exec_command_delete", function () {
    if ($(editor.getSelectionElement()).attr("src")) {
        listDash.push(`${$(editor.getSelectionElement()).attr("src")}`);
        listImages = listImages.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("src")}`);
    } else if ($(editor.getSelectionElement()).attr("href")) {
        listDash.push(`${$(editor.getSelectionElement()).attr("href")}`);
        listImages = listImages.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("href")}`);
    }
    console.log("listDash: ", listDash);
    console.log("listImages: ", listImages);
});

editor.attachEvent("change", function (state, cmd, value) {
    console.log(state, cmd, value);
    if ($(editor.getSelectionElement()).attr("src")) {
        listDash = listDash.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("src")}`);
        if (`${$(editor.getSelectionElement()).attr("src")}`.indexOf("data") < 0 && `${$(editor.getSelectionElement()).attr("src")}`.indexOf("/") == 0) {
            if (listImages.indexOf(`${$(editor.getSelectionElement()).attr("src")}`) < 0) {
                listImages.push(`${$(editor.getSelectionElement()).attr("src")}`);
            }
        }
    } else if ($(editor.getSelectionElement()).attr("href")) {
        listDash = listDash.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("href")}`);
        if (listImages.indexOf(`${$(editor.getSelectionElement()).attr("href")}`) < 0) {
            listImages.push(`${$(editor.getSelectionElement()).attr("href")}`);
        }
    }
    $("#content-no-html").val(editor.getPlainText().toString().replace(/\s+/ig, " ").replace(/\t/, " ").replace(/\n/, ", "));
    console.log("listDash: ", listDash);
    console.log("listImages: ", listImages);
});


explain.attachEvent("exec_command_delete", function () {
    if ($(explain.getSelectionElement()).attr("src")) {
        listDash.push(`${$(explain.getSelectionElement()).attr("src")}`);
        listImages = listImages.filter(item => `${item}` != `${$(explain.getSelectionElement()).attr("src")}`);
    } else if ($(explain.getSelectionElement()).attr("href")) {
        listDash.push(`${$(explain.getSelectionElement()).attr("href")}`);
        listImages = listImages.filter(item => `${item}` != `${$(explain.getSelectionElement()).attr("href")}`);
    }
    console.log("listDash: ", listDash);
    console.log("listImages: ", listImages);
});

explain.attachEvent("change", function (state, cmd, value) {
    console.log(state, cmd, value);
    if ($(explain.getSelectionElement()).attr("src")) {
        listDash = listDash.filter(item => `${item}` != `${$(explain.getSelectionElement()).attr("src")}`);
        if (`${$(explain.getSelectionElement()).attr("src")}`.indexOf("data") < 0 && `${$(explain.getSelectionElement()).attr("src")}`.indexOf("/") == 0) {
            if (listImages.indexOf(`${$(explain.getSelectionElement()).attr("src")}`) < 0) {
                listImages.push(`${$(explain.getSelectionElement()).attr("src")}`);
            }
        }
    } else if ($(explain.getSelectionElement()).attr("href")) {
        listDash = listDash.filter(item => `${item}` != `${$(explain.getSelectionElement()).attr("href")}`);
        if (listImages.indexOf(`${$(explain.getSelectionElement()).attr("href")}`) < 0) {
            listImages.push(`${$(explain.getSelectionElement()).attr("href")}`);
        }
    }
    console.log("listDash: ", listDash);
    console.log("listImages: ", listImages);
});


var currenAvatar = $("#image-preview").attr("src");
var file;
$("#media").on('change', function (e) {
    if (file) {
        URL.revokeObjectURL(file.preview);
    }
    file = e.target.files[0];
    console.log(file);
    $("#image").addClass("hidden");
    $("#media-audio").addClass("hidden");
    $("#media-video").addClass("hidden");
    if ($('#audio').paused == false) {
        $("#audio").pause();
    }
    if ($('#video').paused == false) {
        $("#video").pause();
    }
    file.preview = URL.createObjectURL(file);
    if (file.type.indexOf('image/') >= 0) {
        $("#image").removeClass("hidden");
        $("#image-preview").attr("src", file.preview);
    } else if (file.type.indexOf('audio/') >= 0) {
        $("#audio").attr("src", file.preview);
        $("#audio").attr("type", file.type);
        $("#media-audio").removeClass("hidden");
    } else if (file.type.indexOf('video/') >= 0) {
        $("#video").attr("src", file.preview);
        $("#video").attr("type", file.type);
        $("#media-video").removeClass("hidden");
    }
    $("#icon-upload").addClass("hidden");
    $("#removeMedia").prop("checked", false);
});
window.onbeforeunload = function (e) {
    URL.revokeObjectURL(file.preview);
};


const removeMediaQuestion= () => {
    if(file.preview){
        URL.revokeObjectURL(file.preview);
    }
    $("#image").addClass("hidden");
    $("#media-audio").addClass("hidden");
    $("#media-video").addClass("hidden");
    $("#icon-upload").removeClass("hidden");
    $("#media").val("");
    $("#removeMedia").prop("checked", true);
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
                                                        <input type="radio" value="${data.data[i].id}" onchange="selectLesson(this)" name="lesson" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
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
                                                        <input type="radio" value="${data.data[i].id}" onchange="selectLesson(this)" name="lesson" id="lesson-${data.data[i].id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
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
            $("#dimension").html(htmlDimension);
            if (result != null && result != null && result.length > 0) {
                for (var i = 0; i < result.length; i++) {
                    htmlDimension += ` <option value="${result[i].id}" >${result[i].name}</option>`;
                }
                $("#dimension").html(htmlDimension);
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
                            $("#dimension").html(htmlDimension);
                            if (result != null && result != null && result.length > 0) {
                                for (var i = 0; i < result.length; i++) {
                                    htmlDimension += ` <option value="${result[i].id}" >${result[i].name}</option>`;
                                }
                                $("#dimension").html(htmlDimension);
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

var listAnswers = [];
var pageAnser = 1;
const searchItemFilterAnswer = (element) => {
    $("#list-answer-search").html(``);
    $("#spin-search-answer").removeClass("hidden");
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/question/answer/search",
            data: { subjectId: subjectid, search: $(element).val() },
            dataType: 'json',
            success: function (data) {
                console.log(data);
                var html = ``;
                if (data != null && data.data != null && data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<tr class="bg-white border-b" id="item-search-answer-${data.data[i].id}">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].id}
                                    </th>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].text}
                                    </td>
                                    <td class="px-6 py-4">
                                        ${data.data[i].media ? `<img class="ml-2 max-w-[50px] max-h-[50px]" src="${contextPath}/images/answer/${data.data[i].media}" alt="answer-image"/>` : ''}
                                    </td>
                                    <td class="px-6 py-4 text-center whitespace-nowrap">
                                        <button type="button" onclick="addAnswer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                        </button>
                                        <button type="button" onclick="deleteAnswerOnServer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>                                        
                                        </button>
                                    </td>
                                </tr>`;
                    }
                }
                $("#list-answer-search").html(html);
                $("#spin-search-answer").addClass("hidden");
            },
            error: function (error) {
                console.log(error);
                alert(error.responseJSON.message);
            }
        })
    }, 1000)
}


const openSearchAnswer = () => {
    $("#list-answer-search").html(``);
    $.ajax({
        method: "GET",
        url: contextPath + "/admin/question/answer/search",
        data: { subjectId: subjectid, search: "" },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var html = ``;
            if (data != null && data.data != null && data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    html += `<tr class="bg-white border-b" id="item-search-answer-${data.data[i].id}">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].id}
                                    </th>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].text}
                                    </td>
                                    <td class="px-6 py-4">
                                        ${data.data[i].media ? `<img class="w-10 h-10 rounded" src="${contextPath}/images/answer/${data.data[i].media}" alt="answer-image"/>` : ''}
                                    </td>
                                    <td class="px-6 py-4 text-center whitespace-nowrap">
                                        <button type="button" onclick="addAnswer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                        </button>
                                        <button type="button" onclick="deleteAnswerOnServer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>                                        
                                        </button>
                                    </td>
                                </tr>`;
                }
            }
            $("#list-answer-search").html(html);
        },
        error: function (error) {
            console.log(error);
            alert(error.responseJSON.message);
        }
    })
}


$('#table-answer-search').on('scroll', function () {
    if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
        $("#spin-search-answer").removeClass("hidden");
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/question/answer/search",
            data: { subjectId: subjectid, search: "", page: pageAnser + 1 },
            dataType: 'json',
            success: function (data) {
                pageAnser++;
                var html = $("#list-answer-search").html();
                if (data != null && data.data != null && data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        html += `<tr class="bg-white border-b" id="item-search-answer-${data.data[i].id}">
                                    <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].id}
                                    </th>
                                    <td class="px-6 py-4 font-medium text-gray-900">
                                        ${data.data[i].text}
                                    </td>
                                    <td class="px-6 py-4">
                                        ${data.data[i].media ? `<img class="w-10 h-10 rounded" src="${contextPath}/images/answer/${data.data[i].media}" alt="answer-image"/>` : ''}
                                    </td>
                                    <td class="px-6 py-4 text-center whitespace-nowrap">
                                        <button type="button" onclick="addAnswer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                        </button>
                                        <button type="button" onclick="deleteAnswerOnServer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>                                        
                                        </button>
                                    </td>
                                </tr>`;
                    }
                }
                $("#list-answer-search").html(html);
                $("#spin-search-answer").addClass("hidden");
            },
            error: function (error) {
                console.log(error);
                alert(error.responseJSON.message);
            }
        })
    }
})



const addAnswer = (data) => {
    $("#alert-answer-success").addClass("hidden");
    $("#alert-answer-error").addClass("hidden");
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/question/answer/get",
            data: { id: data },
            dataType: 'json',
            success: function (data) {
                var is_have = false;
                var html = ``;
                for (var i = 0; i < listAnswers.length; i++) {
                    html += `<tr class="bg-white border-b" id="item-answer-${listAnswers[i].id}">
                                        <td class="w-4 p-4">
                                            <div class="flex items-center">
                                                <input id="answer-${listAnswers[i].id}" type="checkbox" value="${listAnswers[i].id}" name="answer-result" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                                <input type="hidden" value="${listAnswers[i].id}" name="answer">
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 font-medium text-gray-900">
                                            ${listAnswers[i].text}
                                        </td>
                                        <td class="px-6 py-4">
                                            ${listAnswers[i].media ? `<img class="ml-4 w-10 h-10 rounded" src="${contextPath}/images/answer/${listAnswers[i].media}" alt="answer-image"/>` : ''}
                                        </td>
                                        <td class="px-6 py-4 text-right">
                                            <button type="button" onclick="editAnswer(${listAnswers[i].id})" 
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                                            </button>
                                            <button type="button" onclick="deleteAnswer(${listAnswers[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                                        
                                            </button>
                                        </td>
                                    </tr>`;
                    if (listAnswers[i].id == data.id) {
                        is_have = true;
                    }
                }
                if (!is_have) {
                    listAnswers.push(data);
                    html += `<tr class="bg-white border-b" id="item-answer-${data.id}">
                                        <td class="w-4 p-4">
                                            <div class="flex items-center">
                                                <input id="answer-${data.id}" type="checkbox" value="${data.id}" name="answer-result" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                                <input type="hidden" value="${data.id}" name="answer">
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 font-medium text-gray-900">
                                            ${data.text}
                                        </td>
                                        <td class="px-6 py-4">
                                            ${data.media ? `<img class="ml-4 w-10 h-10 rounded" src="${contextPath}/images/answer/${data.media}" alt="answer-image"/>` : ''}
                                        </td>
                                        <td class="px-6 py-4 text-right">
                                            <button type="button" onclick="editAnswer(${data.id})" 
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                                            </button>
                                            <button type="button" onclick="deleteAnswer(${data.id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                                        
                                            </button>
                                        </td>
                                    </tr>`;
                }
                $("#list-answer").html(html);
                $("#alert-answer-success").removeClass("hidden");
                $("#message-alert-answer-success").text("Add answer to question success!");
            },
            error: function(error){
                $("#list-answer").html(html);
                $("#alert-answer-error").removeClass("hidden");
                $("#message-alert-answer-error").text(error.responseJSON.message);
            }
        })
    }, 500);
}

const deleteAnswer = (id) => {
    listAnswers = listAnswers.filter(item => item.id != id);
    $(`#item-answer-${id}`).remove();
}



var fileMediaAnswer;
$("#media-answer").on('change', function (e) {
    if (fileMediaAnswer) {
        URL.revokeObjectURL(fileMediaAnswer.preview);
    }
    fileMediaAnswer = e.target.files[0];
    fileMediaAnswer.preview = URL.createObjectURL(fileMediaAnswer);
    $("#media-answer-preview").removeClass("hidden");
    $("#media-answer-preview").attr("src", fileMediaAnswer.preview);
    $("#icon-upload-answer").addClass("hidden");
});
window.onbeforeunload = function (e) {
    URL.revokeObjectURL(fileMediaAnswer.preview);
};


const removeImageCreateAnswer = () => {
    if(fileMediaAnswer){
        URL.revokeObjectURL(fileMediaAnswer.preview);
    }
    $("#media-answer-preview").addClass("hidden");
    $("#icon-upload-answer").removeClass("hidden");
    $("#media-answer").val("");
}

$("#form-create-answer").on("submit", function (event) {
    event.preventDefault();
    $("#create-answer-success").addClass("hidden");
    $("#create-answer-error").addClass("hidden");
    var formData = new FormData(this);
    $.ajax({
        method: 'POST',
        url: contextPath + '/admin/question/answer/create',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            listAnswers.push(data);
            var html = $("#list-answer").html();
            if (data != null && data.id) {
                html += `<tr class="bg-white border-b" id="item-answer-${data.id}">
                                        <td class="w-4 p-4">
                                            <div class="flex items-center">
                                                <input id="answer-${data.id}" type="checkbox" value="${data.id}" name="answer-result" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                                <input type="hidden" value="${data.id}" name="answer">
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 font-medium text-gray-900">
                                            ${data.text}
                                        </td>
                                        <td class="px-6 py-4">
                                            ${data.media ? `<img class="ml-4 w-10 h-10 rounded" src="${contextPath}/images/answer/${data.media}" alt="answer-image"/>` : ''}
                                        </td>
                                        <td class="px-6 py-4 text-right">
                                            <button type="button" onclick="editAnswer(${data.id})" 
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                                            </button>
                                            <button type="button" onclick="deleteAnswer(${data.id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                                        
                                            </button>
                                        </td>
                                    </tr>`;
            }
            $("#list-answer").html(html);
            $("#form-create-answer").trigger("reset");
            $("#media-answer-preview").removeAttr("src");
            $("#icon-upload-answer").removeClass("hidden");
            $("#media-answer-preview").addClass("hidden");
            $("#create-answer-success").removeClass("hidden");
            $("#message-create-answer-success").text("Create answer success!");
            $("#button-open-create-answer").click();
            $("#create-answer-success").addClass("hidden");
        },
        error: function (error) {
            $("#create-answer-error").removeClass("hidden");
            $("#message-create-answer-error").text(error.responseJSON.message);
        }
    });
})


const deleteAnswerOnServer = (id) => {
    $("#button-model-answer-delete").click();
    $("#button-confirm-delete").attr("onclick", `confirmDeleteAnswer(${id})`)
}

const confirmDeleteAnswer = (id) => {
    $("#alert-answer-success").addClass("hidden");
    $("#alert-answer-error").addClass("hidden");
    $("#button-model-answer-delete").click();
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/question/answer/delete",
        data: { id: id },
        dataType: 'json',
        success: function (data) {
            listAnswers = listAnswers.filter(item => item.id != id);
            $(`#item-answer-${id}`).remove();
            $(`#item-search-answer-${id}`).remove();
            $("#alert-answer-success").removeClass("hidden");
            $("#message-alert-answer-success").text("Delete answer success!");
            $("#spin-search-answer").removeClass("hidden");
            $.ajax({
                method: "GET",
                url: contextPath + "/admin/question/answer/search",
                data: { subjectId: subjectid, search: "", page: 1 },
                dataType: 'json',
                success: function (data) {
                     var html = ``;
                    if (data != null && data.data != null && data.data.length > 0) {
                        for (var i = 0; i < data.data.length; i++) {
                            html += `<tr class="bg-white border-b" id="item-search-answer-${data.data[i].id}">
                                        <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                            ${data.data[i].id}
                                        </th>
                                        <td class="px-6 py-4 font-medium text-gray-900">
                                            ${data.data[i].text}
                                        </td>
                                        <td class="px-6 py-4">
                                            ${data.data[i].media ? `<img class="w-10 h-10 rounded" src="${contextPath}/images/answer/${data.data[i].media}" alt="answer-image"/>` : ''}
                                        </td>
                                        <td class="px-6 py-4 text-center">
                                            <button type="button" onclick="addAnswer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd"></path></svg>
                                            </button>
                                            <button type="button" onclick="deleteAnswerOnServer(${data.data[i].id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>                                        
                                            </button>
                                        </td>
                                    </tr>`;
                        }
                    } 
                    $("#list-answer-search").html(html);
                    $("#spin-search-answer").addClass("hidden");
                },
                error: function (error) {
                    console.log(error);
                    alert(error.responseJSON.message);
                }
            })
        },
        error: function (error) {
            $("#alert-answer-error").removeClass("hidden");
            $("#message-alert-answer-error").text(error.responseJSON.message);
        }
    });
}




$("#form-question").on("submit", function (event) {
    event.preventDefault();
    $("#create-question-error").addClass("hidden");
    $("#button-question-model-spinner").click();
    for (var i = 0; i < listDash.length; i++) {
        $("<input />").attr("type", "hidden")
                .attr("name", "listDash")
                .attr("value", listDash[i])
                .appendTo("#form-question");
    }
    for (var i = 0; i < listImages.length; i++) {
        $("<input />").attr("type", "hidden")
                .attr("name", "listImage")
                .attr("value", listImages[i])
                .appendTo("#form-question");
    }
    var formData = new FormData(this);
    $.ajax({
        method: 'POST',
        url: contextPath + '/admin/question/create',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (data) {
            window.location.href = contextPath + "/admin/question";
        },
        error: function (error) {
            $("#create-question-error").removeClass("hidden");
            if(error.responseJSON.message){
                $("#message-create-question-error").text(error.responseJSON.message);
            }else{
                $("#message-create-question-error").text("Can't not create! some thing error");
            }
            $("#button-question-model-spinner").click();
        }
    });
})




const editAnswer = (id) => {
    $("#button-edit-answer-modal").click();
    $("#edit-amswer-id").val("");
    $("#edit-media-answer-preview").removeAttr("src");
    $("#edit-media-answer-preview").addClass("hidden");
    $("#edit-icon-upload-answer").removeClass("hidden");
    $("#edit-text").val("");
    $("#form-edit-answer").addClass("hidden");
    $("#spin-edit-answer").removeClass("hidden");
    $("#edit-answer-error").addClass("hidden");
    $("#edit-answer-success").addClass("hidden");
    setTimeout(function () {
        $.ajax({
            method: "GET",
            url: contextPath + "/admin/question/answer/get",
            data: { id: id },
            dataType: 'json',
            success: function (data) {
                $("#edit-amswer-id").val(id);
                if(data.media){
                    $("#edit-media-answer-preview").removeClass("hidden");
                    $("#edit-icon-upload-answer").addClass("hidden");
                    $("#edit-media-answer-preview").attr("src", contextPath+"/images/answer/"+data.media);
                }
                $("#edit-answer-subject").val(data.subjectId);
                $("#spin-edit-answer").addClass("hidden");
                $("#edit-text").val(data.text);
                $("#form-edit-answer").removeClass("hidden");
            },
            error: function(error){
                $("#edit-answer-error").removeClass("hidden");
                if(error.responseJSON?.message){
                    $("#message-edit-answer-error").text(error.responseJSON.message);
                }else{
                    $("#message-edit-answer-error").text("Can't save answer please try again!");
                }
            }
        })
    }, 500);
}



$("#form-edit-answer").on("submit", function(event){
    event.preventDefault();
    var formData = new FormData(this);
    $("#edit-answer-error").addClass("hidden");
    $("#edit-answer-success").addClass("hidden");
    setTimeout(function () {
        $.ajax({
            method: "POST",
            url: contextPath + "/admin/question/answer/edit",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                $("#edit-answer-success").removeClass("hidden");
                $("#message-edit-answer-success").text("Update success!");
                $(`#item-answer-${data.id}`).html(
                                        `<td class="w-4 p-4">
                                            <div class="flex items-center">
                                                <input id="answer-${data.id}" type="checkbox" value="${data.id}" name="answer-result" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                                <input type="hidden" value="${data.id}" name="answer">
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 font-medium text-gray-900">
                                            ${data.text}
                                        </td>
                                        <td class="px-6 py-4">
                                            ${data.media ? `<img class="ml-4 w-10 h-10 rounded" src="${contextPath}/images/answer/${data.media}" alt="answer-image"/>` : ''}
                                        </td>
                                        <td class="px-6 py-4 text-right">
                                            <button type="button" onclick="editAnswer(${data.id})" 
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                                            </button>
                                            <button type="button" onclick="deleteAnswer(${data.id})" class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                                        
                                            </button>
                                        </td>`
                        )
            },
            error: function(error){
                $("#edit-answer-error").removeClass("hidden");
                if(error.responseJSON?.message){
                    $("#message-edit-answer-error").text(error.responseJSON.message);
                }else{
                    $("#message-edit-answer-error").text("Can't save answer please try again!");
                }
            }
        })
    }, 500);
})


var fileEditMediaAnswer;
$("#edit-media-answer").on('change', function (e) {
    if (fileMediaAnswer) {
        URL.revokeObjectURL(fileEditMediaAnswer.preview);
    }
    fileEditMediaAnswer = e.target.files[0];
    fileEditMediaAnswer.preview = URL.createObjectURL(fileEditMediaAnswer);
    $("#edit-media-answer-preview").removeClass("hidden");
    $("#edit-media-answer-preview").attr("src", fileEditMediaAnswer.preview);
    $("#edit-icon-upload-answer").addClass("hidden");
    $("#remove-image-edit-answer").prop("checked", false);
});
window.onbeforeunload = function (e) {
    URL.revokeObjectURL(fileEditMediaAnswer.preview);
};


const removeImageEditAnswer = () => {
    if(fileEditMediaAnswer){
        URL.revokeObjectURL(fileEditMediaAnswer.preview);
    }
    $("#edit-media-answer-preview").addClass("hidden");
    $("#edit-icon-upload-answer").removeClass("hidden");
    $("#edit-media-answer").val("");
    $("#remove-image-edit-answer").prop("checked", true);
}