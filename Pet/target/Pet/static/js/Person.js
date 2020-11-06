$(document).ready(function () {
    $("#dynamic,#info,#pwd,#order1").hide();

    $(".or").click(function () {
        $("#order").show();
        $("#order").siblings().hide();
    });

    $(".or1").click(function () {
        $("#order1").show();
        $("#order1").siblings().hide();
    });

    $(".in").click(function () {
        $("#info").show();
        $("#info").siblings().hide();
    });

    $(".dy").click(function () {
        $("#dynamic").show();
        $("#dynamic").siblings().hide();
    });

    $(".password").click(function () {
        $("#pwd").show();
        $("#pwd").siblings().hide();
    });
});

