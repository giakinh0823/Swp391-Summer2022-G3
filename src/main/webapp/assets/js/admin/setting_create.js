const showParen = (element, url) => {
    $("#parent-field").addClass("hidden");
    if ($(element).val() == "CATEGORY_SUBJECT" || $(element).val() == "CATEGORY_POST") {
        $.ajax({
            method: "GET",
            url: url,
            data: {code: $(element).val()},
            dataType: 'json',
            success: function (data) {
                if (data.length > 0) {
                    $("#parent-field").removeClass("hidden");
                    var html = `<option value="none">None</option>`;
                    data.forEach(item => {
                        html += `<option value="${item.id}">${item.value}</option>`;
                    });
                    $("#parent").html(html);
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
    }
}


