$.extend({
    // 自定义ajax请求，将未登录，内部错误等统一处理，业务方只需处理正常响应的情况
    "customGet": function (url, params, callback) {
        $.get(url, params, function (response) {
            if (response.code == 200) {
                // 响应正常，将body返回业务方，自行处理
                callback(response.body);
            } else if (response.code == 500) {
                alert(response.message);
            } else if (response.code == 401) {
                // 401，未登录，可以返回登录页面
                alert(response.message);
            } else {
                alert(response.message);
            }
        });
    }
});