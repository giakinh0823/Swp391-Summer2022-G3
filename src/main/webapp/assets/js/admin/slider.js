const changeActiveSlider = (element) => {
    $("#button-confirm-change-status").attr("onclick", `confirmActiveSlider(${$(element).val()},${$(element).is(":checked")})`);
    $(element).prop("checked", !$(element).is(":checked"));
    $("#button-close-change-status").click();
}


const confirmActiveSlider = (id, status) => {
    $("#alert-message").addClass("hidden");
    $.ajax({
        method: "POST",
        url: contextPath + "/admin/slider/status/change",
        data: {id: id, status: status},
        dataType: 'json',
        success: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-red-100 text-red-700");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text(message.message);
            $("#alert-message-code").text(message.code);
            $("#button-close-change-status").click();
            $("#toggle-slider-"+id).prop("checked", status);
        },
        error: function (message) {
            $("#alert-message").removeClass("hidden");
            $("#alert-message").removeClass("bg-green-100 text-green-700");
            $("#alert-message").addClass("bg-red-100 text-red-700");
            $("#alert-message-span").text(JSON.parse(message.responseText).message);
            $("#alert-message-code").text("Error!");
            $("#button-close-change-status").click();
        }
    })
}



const changeCheckbox = () => {
    const checkbox = $("input[name='slider[]']");
    const checkbox_checked = $("input[name='slider[]']:checked");
    $("#checkbox-all-slider").prop("checked", checkbox.length == checkbox_checked.length);
    if (checkbox_checked.length > 0) {
        $("#button-delete-slider-seleted").removeClass("hidden");
    } else {
        $("#button-delete-slider-seleted").addClass("hidden");
    }
}

const changeCheckboxAll = () => {
    const checkboxs = $("input[name='slider[]']");
    checkboxs.each(function () {
        $(this).prop("checked", $("#checkbox-all-slider").is(":checked"));
    })
    if ($("#checkbox-all-slider").is(":checked")) {
        $("#button-delete-slider-seleted").removeClass("hidden");
    } else {
        $("#button-delete-slider-seleted").addClass("hidden");
    }
}


const confirmDeleteSliderSelected = (url) => {
    const checkbox_checked = $("input[name='slider[]']:checked");
    const list = [];
    checkbox_checked.each(function () {
        list.push($(this).val());
    });
    $.ajax({
        method: "POST",
        url: url,
        data: {sliders: list},
        dataType: 'json',
        success: function (message) {
            list.forEach(item => {
                $("#slider-item-" + item).remove();
            })
            $("#button-delete-slider-selected-cancel").click();
            $("#alert-message").removeClass("hidden");
            $("#alert-message").addClass("bg-green-100 text-green-700");
            $("#alert-message-span").text("Delete feature success");
            $("#alert-message-code").text("Success");
            window.location.reload();
        },
        error: function () {
            $("#button-delete-slider-selected-cancel").click();
            $("#alert-message").removeClass()("hidden");
            $("#alert-message").addClass("bg-green-100 text-red-700");
            $("#alert-message-span").text("Delete feature error");
            $("#alert-message-code").text("Error");
        }
    })
}

