//定义两个全局变量 总记录数，当前页
var totalRecord, currentPage;
//界面初始化完成后执行的js
$(function () {
    to_page(1);
    GoodsDetail();
    GoodsEdit();
    //宠物搜索
    getDeps(".uesr_search #addpet1");
    //宠物添加
    getDeps(".f #types");
});
//ajax异步请求要访问的页面，传入页数
function to_page(pn) {
    //解决刷新后checkbox为选中状态
    //发送ajax请求
    $.ajax({
        url: "/Back/goBackGoodsList",
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

//解析并显示订单数据
function build_emps_table(result) {
    //清空表格
    $(".table tbody").empty();
    var goods = result.datas.pageInfo.list;
    $.each(goods, function (index, item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'></td>");
        var gdtitle = $("<td></td>").append(item.gdtitle);
        var gdname = $("<td></td>").append(item.gdname);
        var gdtype = $("<td></td>").append(item.pettype.gtypename);
        var gdtime= $("<td></td>").append(transferTime(item.gdtime));
        //操作下拉
        var look=$("<li></li>").addClass("look_btn").append($("<a href='#'></a>").append("查看"));
        look.attr("lookID",item.gdid);
        var update=$("<li></li>").addClass("update_btn").append($("<a href='#'></a>").append("编辑"));
        update.attr("update",item.gdid);
        var del=$("<li></li>").addClass("del_btn").append($("<a></a>").append("删除"));
        del.attr("del",item.gdid);
        var ul=$("<ul></ul>").addClass("dropdown-menu").append(look).append(update).append(del);
        var btn=$("<button></button>").attr({"data-toggle":"dropdown","role":"button","aria-haspopup":"true","aria-expanded":"false"}).append("操作").addClass("btn btn-primary").append($("<span></span>").addClass("caret "));
        var control = $("<div></div>").attr("role","presentation").addClass("dropdown").append(btn).append(ul);
        var xiala = $("<td></td>").append(control);
        $("<tr></tr>").append(checkBoxTd)
            .append(gdtitle)
            .append(gdname)
            .append(gdtype)
            .append(gdtime)
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
            to_page(1);
        });
        prePageLi.click(function () {
            to_page(result.datas.pageInfo.prePage);
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
            to_page(result.datas.pageInfo.total + 100);
        });
        nextPageLi.click(function () {
            to_page(result.datas.pageInfo.nextPage);
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
            to_page(item)
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


//查看宠物信息
$(document).on("click", ".look_btn", function () {
    var id=$(this).attr("lookID");
    $.ajax({
        url: "/Back/LookGoods/"+id,
        type: "GET",
        success: function (result) {
           window.location.href="/Back/goBackGoodsDetail";
        }
    });
});

function GoodsDetail() {
    $.ajax({
        url: "/Back/LookGoodsOne",
        type: "GET",
        success: function (result) {
            var goods=result.datas.goods;
            $(".gdname").html(goods.gdname);
            $(".gdsex").html(goods.gdsex);
            $(".gdage").html(goods.gdage);
            $(".gdtitle").html(goods.gdtitle);
            $(".gdtype").html(goods.pettype.gtypename);
            $(".gdcon").html(goods.gdcontext);
            $(".img1 img").attr('src',goods.gdimg);
        }
    });
};

//修改宠物信息
$(document).on("click", ".update_btn", function () {
    var id=$(this).attr("update");
    $.ajax({
        url: "/Back/LookGoods/"+id,
        type: "GET",
        success: function (result) {
            window.location.href="/Back/goBackGoodsEdit";
        }
    });
});

//宠物信息传给宠物修改页面
function GoodsEdit() {
    getDeps("#content form #gtype");
    $.ajax({
        url: "/Back/LookGoodsOne",
        type: "GET",
        success: function (result) {
            console.log(result);
            var goods=result.datas.goods;
            $("#name").val(goods.gdname);
            $("#addpet").val(goods.gdsex);
            $("#age").val(goods.gdage);
            $("#title").val(goods.gdtitle);
            $("#gtype").val([goods.gdtype]);
            $("#contents").val(goods.gdcontext);
            $("#gid").val(goods.gdid);
        }
    });
};

//修改宠物信息的提交
$("#change_btn").click(function () {
    $.ajax({
        url: "/Back/GoodsChange",
        data: $("#content form").serialize(),
        type: "PUT",
        success: function (result) {
           window.history.go(-1);
        }
    });
});


//获取宠物类别
function getDeps(ele) {
    $(ele).empty();
    $.ajax({
        url: "GoodsType",
        type: "GET",
        success: function (result) {
            $.each(result.datas.listtype, function (index, item) {
                $("<option></option>").append(item.gtypename).attr("value", item.gid).appendTo(ele);
            });
        }
    });
};

//宠物搜索
$("#Seacher_btn").click(function() {
    $.ajax({
        url: "/Back/GoodsSeacher",
        data: $(".uesr_search").serialize(),
        type: "GET",
        success: function (result) {
            console.log(result);
            if(confirm("是否查看【" + result.datas.goods.gdname + "】信息么？")){
                window.location.href="/Back/goBackGoodsDetail";
            }
        }
    });
});

//宠物品种的添加
$("#btn_insert").click(function() {
    $.ajax({
        url: "/Back/InsertPettype",
        data: $("#TypeModal form").serialize(),
        type: "POST",
        success: function (result) {
           alert("添加成功");
            $('#TypeModal').modal('hide');
        }
    });
});

//宠物品种的添加取消Model数据
$("#pettype").click(function () {
    $("#TypeModal form").empty();
});


