new Vue({
    el: '#news',
    data: {
        list: [],
        cont: [],
        news: [],
        pages: '',
        all: '', //总页数
        cur: '',//当前页码
        totalPage: 0,//当前条数
        pageIndex: [],
        flag: '',
    },
    methods: {
        dataListFn: function (index) {
            let self = this; //解决axios 中 then 内部不能使用Vue的实例化的this 的问
            axios.get('/Index/toNews', {
                params: {
                    pn: index
                }
            }).then(function (response) {
                self.list = response.data.datas.news.list;
                self.all = response.data.datas.news.total;
                self.cur = response.data.datas.news.pageNum;
                self.totalPage = response.data.datas.news.pageSize;
                self.pageIndex = response.data.datas.news.navigatepageNums;
                self.pages = response.data.datas.news.pages;
            });
        },
        //分页
        btnClick: function (data) {//页码点击事件
            if (data != this.cur) {
                this.cur = data
            }
//根据点击页数请求数据
            this.dataListFn(this.cur.toString());
        },
        pageClick: function () {
//根据点击页数请求数据
            this.dataListFn(this.cur.toString());
        },
        lastClick: function () {
            //根据点击未页数请求数据
            this.dataListFn(this.pages.toString());
        },
        look: function (id) {
            window.location.href = "/Index/goPetDetail/" + id;
        },
        lookcon: function (id) {
            window.location.href = "/Index/goConDeatil/" + id;
        },
        looknew: function (id) {
            window.location.href = "/Index/goNewsDetail/" + id;
        },

    },
    created: function () {
        //为了在内部函数能使用外部函数的this对象，要给它赋值了一个名叫self的变量。
        var self = this;
        self.dataListFn(1),
            axios.get('/Index/pet', {
                params: {
                    pn: 3
                }
            }).then(function (response) {
                self.cont = response.data.datas.Goods.list;
            });
        axios.get('/Index/toContent', {
            params: {
                pn: 3
            }
        }).then(function (response) {
            self.cont = response.data.datas.content.list;
        });
    },

});

Vue.filter('dataTime', function (value) {
    console.log(value);
    var value = new Date(value);
    if (!value)
        return '';
    if (value instanceof Date) {
        var d = value;
        var y = d.getFullYear();
        console.log(d);
        var m = d.getMonth() + 1;
        var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
        var myDate = y + '-' + m + '-' + day;
        return myDate;
    } else {
        return value;
    }
});