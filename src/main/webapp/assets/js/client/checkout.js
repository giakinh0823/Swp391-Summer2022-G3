/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


$("#form-checkout").on("submit", function(event){
    event.preventDefault();
    $("#error-checkout").addClass("hidden");
    var form = new FormData(this);
    $.ajax({
        method: "POST",
        url: contextPath+"/course/checkout",
        data: $(this).serialize(),
        dataType: 'json',
        success: function(data){
            window.location.href = contextPath + "/course/register";
        },
        error: function(error){
            $("#error-checkout").removeClass("hidden");
            $("#error-message-checkout").text(error.responseJSON?.message ? error?.responseJSON?.message : "Some thing error!");
        }
    })
})

const setSubjectId(id){
    $("#checkout-subject").val(id);
}