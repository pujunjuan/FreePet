//1. 校验用户名是否符合规范，是否在数据库已存在
$("#phone").focusout(function () {
    var phone = $("#phone").val();
    var regName = /^1[3456789]\d{9}$/;
    if (!regName.test(phone)) {
        show_validate_msg("#phone", "error", "输入正确的号码");
    } else {
        show_validate_msg("#phone", "success", "");
        //进行ajax验证
        valudate_phone(phone);
    }

});
//显示校验后展示信息
function show_validate_msg(ele, status, msg) {
    $(ele).parent().removeClass("has-success has-error");
    $(ele).next("span").text("");
    if ("success" == status) {
        $(ele).parent().addClass("has-success");
    } else if ("error" == status) {
        $(ele).parent().addClass("has-error");
    }
    $(ele).next("span").text(msg);

}
//登录进行是否注册
function valudate_phone(phone) {
    $.ajax({
        url: "checkphone",
        data: "phone=" + phone,
        type: "POST",
        success: function (result) {
            if (result.code == 100) {
                show_validate_msg("#phone", "success", "");
            } else if (result.code == 200) {
                show_validate_msg("#phone", "error", "用户未注册");
            }
        }
    });
}

//注册验证
//手机号码验证
$("#rphone").focusout(function () {
    var phone = $("#rphone").val();
    var regphone = /^1[3456789]\d{9}$/;
    if (!regphone.test(phone)) {
        show_validate_msg("#rphone", "error", "填写正确的电话号码");
    } else {
        show_validate_msg("#rphone", "success", "");
        //进行ajax验证
        check(phone);
    }
});
function check(phone) {
    $.ajax({
        url: "checkp",
        data: "phone=" + phone,
        type: "POST",
        success: function (result) {
            if (result.code == 100) {
                show_validate_msg("#rphone", "success", "");
            } else if (result.code == 200) {
                show_validate_msg("#rphone", "error", "号码已存在");
            }
        }
    });
}

//昵称校监
function checkMenbership(Menbership) {
    $.ajax({
        url: "checkMenbership",
        data: "Menbership=" + Menbership,
        type: "POST",
        success: function (result) {
            if (result.code == 100) {
                show_validate_msg("#Menbership", "success", "");
            } else if (result.code == 200) {
                show_validate_msg("#Menbership", "error", "昵称已存在");
            }
        }
    });
}

//2. 校验昵称是否符合规范
$("#Menbership").focusout(function () {
    var Menbership = $("#Menbership").val();
    var regMenbership = /[\u4E00-\u9FA5]/;
    if (!regMenbership.test(Menbership)) {
        show_validate_msg("#Menbership", "error", "请输入正确的昵称");
    } else {
        show_validate_msg("#Menbership", "success", "");
        checkMenbership(Menbership);
    }

});

//注册传值判断
//2. 校验昵称是否符合规范
$("#btn_submit").click(function () {
    var Menbership = $("#Menbership").val();
    var name = $("#uName").val();
    var phone = $("#rphone").val();
    var gender=$("input[name='sex']:checked").val();
    var age = $("#age").val();
    var address=$("#address").val();
    var password1=$("#password1").val();
    var password2=$("#password2").val();
    if(password2!=password1){
        alert("密码不一致");
    }
    if(Menbership!=""&&name!=""&&phone!=""&&age!=""&&address!=""&&password1!=""&&password2!=""){
        alert("请把信息填写完整");
    }

});


//宠物申请
const id =parseInt($("#aid").val());
$('#apply_btn').one('click',function(){
    const con = $("#textarea").val();
        if(con==null){
            alert("申请理由不能为空");
            $('#myModal').modal('hide');
        }else {
            $.ajax({
                url: "/Index/ApplyPet/"+id,
                type: "POST",
                data:"addname="+con,
                success: function (result) {
                    console.log(result);
                    if(result.code == 100){
                        //关闭模态框
                        $('#myModal').modal('hide');
                        $("#apply_btn").attr('disabled',true);
                        window.location.reload();
                    }else {
                        alert("数据加载中")
                    }
                }
            });
        }


});

$('.queding').click(function(){

    //执行自己要做的事情

    //移出掉this的click事件
    $(this).off('click');
    //移出掉关闭按钮的事件
    $('.guan').off();
});

//点击关闭按钮的时候，清楚掉保存和关闭按钮的绑定钩子事件
$('.guan').click(function(){
    //移出关闭按钮的绑定事件
    $(this).off();
    //同时移出确定按钮的绑定事件
    $('.queding').off();
});


    $('#btn_login').click(function () {
            setTimeout(function () {
                var info=$("#alert>span").html();
                console.log(info);
                if(info!=null){
                    $("alert").show();
                }
            }, 1000);
    })

