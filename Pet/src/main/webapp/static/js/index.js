

new Vue({
    el: '#show',
    data: {
        list: [],
        cont:[],
        news:[],
    },
    methods:{
        look:function (id) {
            window.location.href="/Index/goPetDetail/"+id;
        },
        lookcon:function (id) {
            window.location.href="/Index/goConDeatil/"+id;
        },
        looknew:function (id) {
            window.location.href="/Index/goNewsDetail/"+id;
        },
    },
    created: function () {
        //为了在内部函数能使用外部函数的this对象，要给它赋值了一个名叫self的变量。
        var self = this;
        axios.get('/Index/toContent', {
            params: {
                pn: 1
            }
        }).then(function (response) {
            self.cont = response.data.datas.content.list;
        });
        axios.get('/Index/pet', {
            params: {
                pn: 1
            }
        }).then(function (response) {
            self.cont = response.data.datas.Goods.list;
        });

        axios.get('/Index/toNews', {
            params: {
                pn: 1
            }
        }).then(function (response) {
            self.cont = response.data.datas.news.list;
        });
    },

});
Vue.filter('dataTime', function(value) {
    console.log(value);
    var value = new Date(value);
    if (!value)
        return '';
    if (value instanceof Date) {
        var d = value;
        var m = d.getMonth() + 1;
        var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
        var myDate = m + '-' + day;
        return myDate;
    } else {
        return value;
    }
});

