$(function () {
    var moneys = $(".money");
    $.each(moneys, function (idx, ele) {
        var money = $(ele);
        money.html(formatMoney(money.html()));
    });
});

/**
 * 金额格式化
 * 格式化为千分位逗号分隔
 *
 * @param money 待格式化的金额
 * @param n 保留的小数位数
 */
function formatMoney(money, n) {
    if (!money) {
        return;
    }
    n = n > 0 && n <= 20 ? n : 2;
    money = parseFloat(money).toFixed(n) + "";
    var l = money.split(".")[0].split("").reverse();
    var r = money.split(".")[1];
    var t = "";
    for(var i = 0; i < l.length; i ++ )
    {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}