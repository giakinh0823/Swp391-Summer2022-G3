var currenAvatar = $("#image-preview").attr("src");
var file;
$("#thumbnail").on('change', function (e) {
    if (file) {
        URL.revokeObjectURL(file.preview);
    }
    file = e.target.files[0];
    file.preview = URL.createObjectURL(file);
    $("#image-preview").attr("src", file.preview);
});

window.onbeforeunload = function (e) {
    URL.revokeObjectURL(file.preview);
};

var listImages = []

var editorcfg = {}

editorcfg.file_upload_handler = function (file, callback, optionalIndex, optionalFiles) {
    var uploadhandlerpath = contextPath + "/admin/subject/lesson/media/uploads";

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
    formData.append("id", postId);
    xh.send(formData);
}


editorcfg.tabSpaces = "&nbsp;&nbsp;&nbsp;";
var editor = new RichTextEditor("#content", editorcfg);


editor.attachEvent("exec_command_delete", function () {
    if($(editor.getSelectionElement()).attr("src")){
        if (`${$(editor.getSelectionElement()).attr("src")}`.indexOf("data")<0) {
            listImages.push($(editor.getSelectionElement()).attr("src"));
        }
    }else if($(editor.getSelectionElement()).attr("href")){
        listImages.push($(editor.getSelectionElement()).attr("href"));
    }
    console.log(listImages);
});

editor.attachEvent("change", function (state, cmd, value) {
    console.log(state, cmd, value);
    if($(editor.getSelectionElement()).attr("src")){
        listImages = listImages.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("src")}`);
    }else if($(editor.getSelectionElement()).attr("href")){
        listImages = listImages.filter(item => `${item}` != `${$(editor.getSelectionElement()).attr("href")}`);
    }
    console.log(listImages);
});


$("#form-post").on("submit", function (event) {
    event.preventDefault();
    if (listImages!=null && listImages.length > 0) {
        $.ajax({
            method: "POST",
            url: contextPath + "/admin/subject/lesson/media/delete",
            data: {images: listImages},
            dataType: 'html',
            success: function (data) {
                console.log(data);
            },
        });
    }
    this.submit();
})


$("#type").on("change",function(event){
   $("#topic-layout").addClass("hidden");
   $("#quizzes-layout").addClass("hidden");
   $("#youtube-layout").addClass("hidden");
   $("#content-layout").addClass("hidden");
   
   console.log($("#type :selected").text());
   if($("#type :selected").text().toString().toLowerCase().indexOf("topic") >= 0){
       $("#quizzes-layout").removeClass("hidden");
   } 
   if($("#type :selected").text().toString().toLowerCase().indexOf("lesson")>= 0){
        $("#topic-layout").removeClass("hidden");
       $("#content-layout").removeClass("hidden");
       $("#youtube-layout").removeClass("hidden");
   }
   if($("#type :selected").text().toString().toLowerCase().indexOf("quiz")>=0){
       $("#topic-layout").removeClass("hidden");
       $("#quizzes-layout").removeClass("hidden");
   }
});