/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


var currenAvatar = $("#image-preview").attr("src");
var file;
$("#image").on('change', function (e) {
    if (file) {
        URL.revokeObjectURL(file.preview);
    }
    file = e.target.files[0];
    file.preview = URL.createObjectURL(file);
    $("#image-preview").attr("src", file.preview);
     $("#icon-upload").addClass("hidden");
});
window.onbeforeunload = function (e) {
    URL.revokeObjectURL(file.preview);
};