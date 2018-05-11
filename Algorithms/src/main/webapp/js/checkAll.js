
// 全选
function checkAll(input) {
    var checked = $(input).is(':checked');
    $("input[name=idList]").attr("checked", function(){
        this.checked = checked;
    });
}

// 检查全选
function changeCheck() {
    var idList = $("input[name=idList]");
    var allcheck = $("#allcheck");
    var flag = true;
    $.each(idList, function(index, ele) {
        if (!$(ele).is(":checked")) {
            flag = false;
        }
    });
    if (flag) {
        allcheck.attr("checked", function(){
            this.checked = true;
        });
    } else {
        allcheck.attr("checked", function(){
            this.checked = false;
        });
    }
}
