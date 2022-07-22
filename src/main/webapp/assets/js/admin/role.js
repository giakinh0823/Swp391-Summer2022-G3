
const changeCheckbox = () => {
    const checkbox = $("input[name='feature[]']");
    const checkbox_checked = $("input[name='feature[]']:checked");
    $("#checkbox-all-feature").prop("checked", checkbox.length == checkbox_checked.length);
    if (checkbox_checked.length > 0) {
        $("#button-delete-feature-seleted").removeClass("hidden");
    } else {
        $("#button-delete-feature-seleted").addClass("hidden");
    }
}


const changeCheckboxAll = () => {
    const checkboxs = $("input[name='feature[]']");
    checkboxs.each(function () {
        $(this).prop("checked", $("#checkbox-all-feature").is(":checked"));
    })
    if ($("#checkbox-all-feature").is(":checked")) {
        $("#button-delete-feature-seleted").removeClass("hidden");
    } else {
        $("#button-delete-feature-seleted").addClass("hidden");
    }
}

const confirmDeleteFeatureSelected = (url) => {
    const checkbox_checked = $("input[name='feature[]']:checked");
    const list = [];
    checkbox_checked.each(function () {
        list.push($(this).val());
    });
    $.ajax({
        method: "POST",
        url: url,
        data: {features: list},
        dataType: 'json',
        success: function (message) {
            list.forEach(item => {
                $("#feature-item-" + item).remove();
            })
            $("#button-delete-feature-selected-cancel").click();
            $("#alert-message").removeClass("hidden");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text("Delete feature success");
            $("#alert-message-code").text("Success");
            window.location.reload();
        },
        error: function () {
            $("#button-delete-feature-selected-cancel").click();
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
        }
    })
}
