//定义两个全局变量 总记录数，当前页
var totalRecord, currentPage;
//界面初始化完成后执行的js
$(function () {
    to_User_page(1);
    UserDetail();
});
//ajax异步请求要访问的页面，传入页数
function to_User_page(pn) {
    //解决刷新后checkbox为选中状态
    //发送ajax请求
    $.ajax({
        url: "/Back/UserList",
        data: "pn=" + pn,
        type: "GET",
        success: function (result) {
            //1、解析并显示订单数据
            build_emps_table(result);
            //2、解析并显示分页信息
            build_page_info(result);
            //3、解析显示分页条数据
            build_page_nav(result);
        }
    });

}

//解析并显示用户数据
function build_emps_table(result) {
    //清空表格
    $(".table tbody").empty();
    var user = result.datas.pageInfo.list;
    $.each(user, function (index, item) {
        var uID = $("<th></th>").append(item.uid);
        var uname= $("<td></td>").append(item.uname);
        var umenber = $("<td></td>").append(item.umenbership);
        var uphone = $("<td></td>").append(item.uphone);
        var uaddress = $("<td></td>").append(item.uaddress);
        //操作下拉
        var look=$("<li></li>").addClass("look_btn").append($("<a href='#'></a>").append("查看用户"));
        look.attr("look",item.uid);
        var del=$("<li></li>").addClass("del_btn").append($("<a></a>").append("删除用户"));
        del.attr("del",item.uid);
        var ul=$("<ul></ul>").addClass("dropdown-menu").append(look).append(del);
        var btn=$("<button></button>").attr({"data-toggle":"dropdown","role":"button","aria-haspopup":"true","aria-expanded":"false"}).append("操作").addClass("btn btn-primary").append($("<span></span>").addClass("caret "));
        var control = $("<div></div>").attr("role","presentation").addClass("dropdown").append(btn).append(ul);
        var xiala = $("<td></td>").append(control);
        $("<tr></tr>").append(uID)
            .append(umenber)
            .append(uname)
            .append(uphone)
            .append(uaddress)
            .append(xiala)
            .appendTo(".table tbody");
    });
}

//构建分页信息
function build_page_info(result) {
    var pageInfo = result.datas.pageInfo;
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第 " + pageInfo.pageNum + " 页,总计 " + pageInfo.pages + " 页,总 " + pageInfo.total + " 条记录");
    totalRecord = pageInfo.total;
    currentPage = pageInfo.pageNum;
}

//构建分页条信息
function build_page_nav(result) {
    //每次写入前先清空
    $("#page_nav_area").empty();
    //构建元素
    var nav = $("<nav></nav>").attr("aria-label", "Page navigation");
    var ul = $("<ul></ul>").addClass("pagination");

    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //判断是否有前一页
    if (result.datas.pageInfo.hasPreviousPage) {
        firstPageLi.click(function () {
            to_User_page(1);
        });
        prePageLi.click(function () {
            to_User_page(result.datas.pageInfo.prePage);
        });
    }
    else {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }

    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"))
    //判断是否有下一页
    if (result.datas.pageInfo.hasNextPage) {
        lastPageLi.click(function () {
            to_User_page(result.datas.pageInfo.total + 100);
        });
        nextPageLi.click(function () {
            to_User_page(result.datas.pageInfo.nextPage);
        });
    }
    else {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }
    //添加首页和前一页
    ul.append(firstPageLi).append(prePageLi);

    //遍历给ul中添加页码提示
    $.each(result.datas.pageInfo.navigatepageNums, function (index, item) {
        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.datas.pageInfo.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            to_User_page(item)
        });
        ul.append(numLi);
    });
    //添加下一页和最后一页
    ul.append(nextPageLi).append(lastPageLi);
    nav.append(ul).appendTo("#page_nav_area");
}

//改变时间
function transferTime(cTime) {     //将json串的一串数字进行解析
    var jsonDate = new Date(parseInt(cTime));
    //       alert(jsonDate);
    //为Date对象添加一个新属性，主要是将解析到的时间数据转换为我们熟悉的“yyyy-MM-dd hh:mm:ss”样式
    Date.prototype.format = function(format) {
        var o = {
            //获得解析出来数据的相应信息，可参考js官方文档里面Date对象所具备的方法
            "y+": this.getFullYear(), //得到对应的年信息
            "M+": this.getMonth() + 1, //得到对应的月信息，得到的数字范围是0~11，所以要加一
            "d+": this.getDate(), //得到对应的日信息
            "h+": this.getHours(), //得到对应的小时信息
            "m+": this.getMinutes(), //得到对应的分钟信息
            "s+": this.getSeconds(), //得到对应的秒信息
        }
        //将年转换为完整的年形式
        if(/(y+)/.test(format)) {
            format = format.replace(RegExp.$1,    (this.getFullYear() + "") .substr(4 - RegExp.$1.length));
        }
        //连接得到的年月日 时分秒信息
        for(var k in o) {
            if(new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1,  RegExp.$1.length == 1 ? o[k] : ("00" + o[k]) .substr(("" + o[k]).length));
            }
        }
        return format;
    }
    var newDate = jsonDate.format("yyyy-MM-dd hh:mm:ss");
    return newDate;
}

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

//用户详情显示
function UserDetail(){
    $.ajax({
        url: "/Back/UserLookOne",
        type: "GET",
        success: function (result) {
            var user=result.datas.User;
            $(".udName").html(user.uname);
            $(".udSex").html(user.usex);
            $(".udMenber").html(user.umenbership);
            $(".udAge").html(user.uage);
            $(".udAddress").html(user.uaddress);
            $(".udPhone").html(user.uphone);
        }
    })
}

//查看用户
$(document).on("click", ".look_btn", function () {
  var id=$(this).attr("look");
  window.location.href="/Back/goUserDetail";
    $.ajax({
        url: "/Back/UserLook/"+id,
        type: "GET",
        success: function (result) {

        }
    });
});
//用户删除
$(document).on("click", ".del_btn", function () {
    var id=$(this).attr("del");
    $.ajax({
        url: "/Back/UserDelete/"+id,
        type: "DELETE",
        success: function (result) {
            to_User_page(1);
        }
    });
});


//添加用户
$("#user_add_btn").click(function () {


    //电话号码检验
    var regphone = /^1[3456789]\d{9}$/;
    //昵称检验
    var regMenbership = /[\u4E00-\u9FA5]/;
    //进行非空验证
    if ($("#Menbership").val() == "" && (!regMenbership.test($("#Menbership").val()))) {
        show_validate_msg("#Menbership", "error", "请填写昵称！！");
        return false;
    } else if ($("#uName").val() == "") {
        show_validate_msg("#uName", "error", "请填写姓名！！");
        return false;
    } else if ($("#age").val() == "") {
        show_validate_msg("#age", "error", "请填写年龄！！");
        return false;
    }else if ($("#phone").val() == ""   && (!regphone.test($("#phone").val())) ) {
        show_validate_msg("#phone", "error", "请填可用的电话号码！！");
        return false;
    }else if ($("#address").val() == "") {
        show_validate_msg("#address", "error", "请填可用的地址！！");
        return false;
    }else if ($("#password1").val() == "") {
        show_validate_msg("#password1", "error", "请输入密码啊！！");
        return false;
    } else if (($("#Menbership").parent().hasClass("has-error")) || ($("#uName").parent().hasClass("has-error")) || ($("#uName").parent().hasClass("has-error"))
    || ($("#age").parent().hasClass("has-error")) ||($("#phone").parent().hasClass("has-error")) ||($("#address").parent().hasClass("has-error"))
    || ($("#password1").parent().hasClass("has-error"))) {
        alert("请填写正确的信息！");
        return false;
    }

    //发送ajax，保存数据
    $.ajax({
        url: "UserAdd",
        type: "POST",
        data:  $("#myModal form").serialize(),
        success: function (result) {
            if (result.code == 100) {//添加成功
                //关闭模态框
                $('#myModal').modal('hide');
                //返回最后一页
                to_User_page(totalRecord);
            } else {
                //显示错误信息
                if (undefined != result.datas.JSR303Error.umenbership) {
                    show_validate_msg("#Menbership", "error", result.datas.JSR303Error.umenbership);
                }
                if (undefined != result.datas.JSR303Error.uname) {
                    show_validate_msg("#uName", "error", result.datas.JSR303Error.uname);
                }
                if (undefined != result.datas.JSR303Error.uage) {
                    show_validate_msg("#age", "error", result.datas.JSR303Error.uage);
                }
                if (undefined != result.datas.JSR303Error.uaddress) {
                    show_validate_msg("#address", "error", result.datas.JSR303Error.uaddress);
                }
                if (undefined != result.datas.JSR303Error.uphone) {
                    show_validate_msg("#phone", "error", result.datas.JSR303Error.uphone);
                }
                if (undefined != result.datas.JSR303Error.upassoword) {
                    show_validate_msg("#password1", "error", result.datas.JSR303Error.upassoword);
                }
            }
        }
    });

});

//清空表单样式及内容
function reset_form(ele) {
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}

//添加员工模态框调用方法
$("a.add").click(function () {
    reset_form('#myModal form');
});