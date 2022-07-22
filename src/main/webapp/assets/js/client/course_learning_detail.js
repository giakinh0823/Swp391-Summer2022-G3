/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function doneVideo() {
    console.log("Video done");
    if (!$("lesson-done-" + lessonId).prop("checked")) {
        $.ajax({
            method: "POST",
            url: contextPath + "/course/learning/done",
            data: {lessonId: lessonId},
            dataType: 'json',
            success: function (data) {
                window.location.href = nextUrl;
            }
        });
    } else {
        window.location.href = nextUrl;
    }
}


if (videoSrc) {
    var tag = document.createElement('script');

    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

    // 3. This function creates an <iframe> (and YouTube player)
    //    after the API code downloads.
    var player;
    function onYouTubeIframeAPIReady() {
        player = new YT.Player('lesson-video', {
            width: '100%',
            videoId: videoSrc.replace("https://www.youtube.com/embed/", ""),
            playerVars: {
                'playsinline': 1
            },
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
    }

    function onPlayerReady(event) {
        event.target.playVideo();
    }

    function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.ENDED) {
            setTimeout(doneVideo, 2000);
        }
    }
}

