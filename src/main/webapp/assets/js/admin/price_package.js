const changeActivePricePackage = (element) => {
    $("#button-confirm-change-status").attr("onclick", `confirmActivePricePackage(${$(element).val()},${$(element).is(":checked")})`);
    $(element).prop("checked", !$(element).is(":checked"));
    $("#button-close-change-status").click();
}

const confirmActivePricePackage = (id, status) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/subject/"+subjectId+"/price-package/status/change",
        data: {id: id, status: status},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text("Success!");
            $("#button-close-change-status").click();
            $("#toggle-price-package-"+id).prop("checked", status);
        },
        error: function (message, error) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
            $("#button-close-change-status").click();
        }
    })
}