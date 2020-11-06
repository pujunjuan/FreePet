//定义两个全局变量 总记录数，当前页
var totalRecord, currentPage;
//界面初始化完成后执行的js
$(function () {
    to_Helpage(1);
    HelpClick();
});

//订单转让申请
//ajax异步请求要访问的页面，传入页数
function to_Helpage(pn) {
    //解决刷新后checkbox为选中状态
    //发送ajax请求
    $.ajax({
        url: "/Back/OrderHelplist",
        data: "pn=" + pn,
        type: "GET",
        success: function (result) {
            //1、解析并显示订单数据
            build_OrderHelp_table(result);
            //2、解析并显示分页信息
            build_page_info(result);
            //3、解析显示分页条数据
            build_page_nav(result);
        }
    });

}
//订单转让申请
//解析并显示订单数据
function build_OrderHelp_table(result) {
    //清空表格
    $("#OrderHelp_table tbody").empty();
    var orderhelp = result.datas.pageInfo.list;
    $.each(orderhelp, function (index, item) {
        var oID = $("<td></td>").addClass("id_btn").append(item.pid);
        var Ocreater = $("<td></td>").append(item.user.uname);
        var time=transferTime(item.ptime);
        var oTime = $("<td></td>").append(time);
        var pname = $("<td></td>").append(item.pname);
        //操作下拉
        var agree=$("<li></li>").addClass("Helpagree_btn").append($("<a href='#'></a>").append("同意"));
        agree.attr("HelpagreeID",item.pid);
        var disagree=$("<li></li>").addClass("Helpdisagree_btn").append($("<a href='#'></a>").append("拒绝"));
        disagree.attr("Helpdisagree",item.pid);
        var detail=$("<li></li>").addClass("Helpdetail_btn").append($("<a></a>").append("查看详情"));
        detail.attr("Helpdetail",item.pid);
        var ul=$("<ul></ul>").addClass("dropdown-menu").append(agree).append(disagree).append(detail);
        var btn=$("<button></button>").attr({"data-toggle":"dropdown","role":"button","aria-haspopup":"true","aria-expanded":"false"}).append("操作").addClass("btn btn-primary").append($("<span></span>").addClass("caret "));
        var control = $("<div></div>").attr("role","presentation").addClass("dropdown").append(btn).append(ul);
        var xiala = $("<td></td>").append(control);
        $("<tr></tr>").append(oID)
            .append(Ocreater)
            .append(oTime)
            .append(pname)
            .append(xiala)
            .appendTo("#OrderHelp_table tbody");
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
            to_Helpage(1);
        });
        prePageLi.click(function () {
            to_Helpage(result.datas.pageInfo.prePage);
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
            to_Helpage(result.datas.pageInfo.total + 100);
        });
        nextPageLi.click(function () {
            to_Helpage(result.datas.pageInfo.nextPage);
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
            to_Helpage(item)
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


function HelpClick() {
    $.ajax({
        url: "/Back/goOrderHelpDetails",
        type: "GET",
        dataType: "JSON",
        async: true, // 默认true(异步请求)
        cache: true,
        success: function (result) {
            $(".huname").html(result.datas.Pet.user.uname);
            $(".id_btn").html(result.datas.Pet.pid);
            $(".husex").html(result.datas.Pet.user.usex);
            $(".humenbership").html(result.datas.Pet.user.umenbership);
            $(".huage").html(result.datas.Pet.user.uage);
            $(".huphone").html(result.datas.Pet.user.uphone);
            $(".huaddress").html(result.datas.Pet.user.uaddress);
            $(".Aname").html(result.datas.Pet.pname);
            $(".asex").html(result.datas.Pet.psex);
            $(".atype").html(result.datas.Pet.ptype);
            $(".aage").html(result.datas.Pet.page);
            $(".atitle").html(result.datas.Pet.ptitle);
            $(".aimgs").attr('src', result.datas.Pet.pmanner);
        }
    });

}

//订单详情页面
$(document).on("click", ".Helpdetail_btn", function () {
    var id=$(this).attr("Helpdetail");
    location.href="/Back/goOrderHelpDetail";
    $.ajax({
        url: "/Back/OrderHelpDetail/"+id,
        type: "GET",
        success: function (result) {

        }
    });
    //3、把员工的id传递给模态框的更新按钮
    $("#emp_update_btn").attr("edit-id", id);
});

//订单同意
$(document).on("click", ".Helpagree_btn", function () {
    var id=$(".id_btn").html();
    $.ajax({
        url: "/Back/OrderHelpAgree/"+id,
        type: "POST",
        success: function (result) {
            var name=window.location.pathname;
            if(name == "/Back/goOrderHelpDetail"){
                window.history.back(-1);
            }
            else {
                to_Helpage(1);
            }
        }
    });
});
//订单拒绝
$(document).on("click", ".Helpdisagree_btn", function () {
    var id=$(".id_btn").html();
    alert(id);
    $.ajax({
        url: "/Back/OrderHelpDelete/"+id,
        type: "POST",
        success: function (result) {
            var name=window.location.pathname;
            if(name == "/Back/goOrderHelpDetail"){
                window.history.back(-1);
            }
            else {
                to_Helpage(1);
            }

        }
    });
});
