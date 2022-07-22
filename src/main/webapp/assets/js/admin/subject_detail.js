var currenAvatar = $("#image-preview").attr("src");
var file;
$("#image").on('change', function (e) {
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