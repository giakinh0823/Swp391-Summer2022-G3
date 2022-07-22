const changeActive = (element, url) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: url,
        data: {id: $(element).val(), status: $(element).is(":checked")},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text("Success!");
        },
        error: function (message, error) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
        }
    })
}

