var currenAvatar = $("#avatar-img").attr("src");
var file;
$("#avatar").on('change', function (e) {
    if (file) {
        URL.revokeObjectURL(file.preview);
    }
    file = e.target.files[0];
    file.preview = URL.createObjectURL(file);
    $("#avatar-img").attr("src", file.preview);
});
window.onbeforeunload = function (e) {
    URL.revokeObjectURL(file.preview);
};