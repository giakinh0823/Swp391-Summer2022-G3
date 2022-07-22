$("#category-button").on('click', () => {
    if ($("#category-menu").hasClass("hidden")) {
        $("#category-menu").removeClass("hidden");
    } else {
        $("#category-menu").addClass("hidden");
    }
})

$(document).mouseup(function (e)
{
    var container = $("category-menu");
    if (!container.is(e.target) && container.has(e.target).length === 0)
    {
        $("#category-menu").addClass("hidden");
    }
});


$("#pop-up-login").on('submit', function (event) {
    event.preventDefault();

    var form = $(this);

    var actionUrl = form.attr('action');

    $("#pop-up-login-alert").addClass("hidden");
    $("#spinner-login").removeClass("hidden");
    $.ajax({
        type: "POST",
        url: actionUrl,
        data: form.serialize(),
        dataType: 'json',
        success: function (data)
        {
            window.location.pathname = data.message;
        },
        error: function(error){
            $("#spinner-login").addClass("hidden");
            $("#pop-up-login-alert").removeClass("hidden");
            $("#pop-up-login-message").text(error.responseJSON?.message ? error?.responseJSON?.message : "Some thing error!");
        }
    });
})


$("#pop-up-signup").on('submit', function (event) {
    event.preventDefault();

    var form = $(this);

    var actionUrl = form.attr('action');
    $("#pop-up-signup-alert").addClass("hidden");
    $("#spinner-signup").removeClass("hidden");
    $.ajax({
        type: "POST",
        url: actionUrl,
        data: form.serialize(),
        dataType: 'json',
        success: function (data)
        {
            window.location.pathname = data.message;
        },
        error: function(error){
            $("#pop-up-signup-alert").removeClass("hidden");
            $("#pop-up-signup-message").text(error.responseJSON?.message ? error?.responseJSON?.message : "Some thing error!");
            $("#spinner-signup").addClass("hidden");
        }
    });
})