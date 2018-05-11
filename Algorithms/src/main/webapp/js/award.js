$(function(){
    // 验证金额格式，最多2位小数
    jQuery.validator.addMethod("isMoney", function(value, element) {
        var money = /^\d+(?:.\d{1,2})?$/;
        return this.optional(element) || (money.test(value));
    }, "请输入正确的费率,最多保留2位小数");

    $("#awardUpdateForm").validate({
        errorElement: 'div',
        errorClass: 'help-block',
        focusInvalid: true,
        onsubmit: true,
        rules: {
            nextCommercialEffectTime: {
                required: true
            },
            nextCommercialReturnRate: {
                required: true,
                isMoney: true,
                range: [0, 99.99]
            },
            nextCompulsoryEffectTime: {
                required: true
            },
            nextCompulsoryReturnRate: {
                required: true,
                isMoney: true,
                range: [0, 99.99]
            }
        },
        messages: {
            nextCommercialEffectTime: {
                required: "请选择商业险生效时间"
            },
            nextCommercialReturnRate: {
                required: "请输入商业险费率",
                isMoney: "请输入正确的商业险费率,最多保留2位小数",
                range: "请输入[0-99.99]之间的数字"
            },
            nextCompulsoryEffectTime: {
                required: "请选择交强险生效时间"
            },
            nextCompulsoryReturnRate: {
                required: "请输入交强险费率",
                isMoney: "请输入正确的交强险费率,最多保留2位小数",
                range: "请输入[0-99.99]之间的数字"
            }
        },
        invalidHandler: function (form, validator) {  //不通过回调
            return false;
        },
        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            /*var span = $(e).parent().children('span');
            if (span) {
                span.remove();
                // setTimeout($(e).parent().children('div').before("<span>%</span>"), 1000);
                setTimeout($(e).after("<span>%</span>"), 1000);

            }*/
        },
        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error');
            $(e).remove();
        },
        errorPlacement: function (error, element) {
            var span = $(element).parent().children('span');
            if (span) {
                error.appendTo(span);
            }
        }
    });

    // 模态框隐藏后的事件,同时隐藏时间选择控件
    $('#myModal').on('hidden.bs.modal', function (e) {
        $dp.hide();
    })
});

//index 跳转的目标页，total总页数
var toPage = function(index, total) {
	index = index < 1 ? 1 : index;
	index = index > total ? total : index;
	
	$("#page").val(index);
	$("#awardSearchForm").submit();
};

function changePageSize(pageSize) {
    $("#pageSize").val(pageSize);
    $("#awardSearchForm").submit();
}

/**
 * 限制只能输入几位小数
 * @param id input的id值
 * @param count 需要保留的小数位数
 */
function isNumber(id, count){
    var m = "";
    var input = $("#" + id);
    var str = input.val();
    if(str.length==1){
        if(str=="-") {
            input.val("");
            return;
        }
    }else if(str.length>1){
        var array = str.split("");
        if(array[0]=="-"){
            str = str.substring(1,str.length);
            // m = "-";
        }
    }
    str = str.replace(/[^\d.]/g, "");
    str = str.replace(/^\./g, "");
    str = str.replace(/\.{2,}/g, ".");
    str = str.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    if (count == 6) {
        str = str.replace(/([0-9]+\.[0-9]{6})[0-9]*/, "$1");
    } else {
        str = str.replace(/([0-9]+\.[0-9]{2})[0-9]*/, "$1");
    }
    input.val(m+str);
}

function initDialogData(id, btn) {
    var awardUpdateForm = $("#awardUpdateForm");
    $("#awardId").val(id);
    $("#nextCommercialReturnRate").val($("#nextCommercialReturnRate" + id).html());
    $("#nextCommercialEffectTime").val($("#nextCommercialEffectTime" + id).html());
    $("#nextCompulsoryReturnRate").val($("#nextCompulsoryReturnRate" + id).html());
    $("#nextCompulsoryEffectTime").val($("#nextCompulsoryEffectTime" + id).html());

    var channelTd = $(btn).closest("td").siblings().first();
    var cityTd = channelTd.next();
    var companyTd = cityTd.next();
    $("#dialogTitleSpan").html("["+channelTd.html()+"-"+cityTd.html()+"-"+companyTd.html()+"]");
}

function updateAward() {
    var awardUpdateForm = $("#awardUpdateForm");
    /*if (!awardUpdateForm.valid()) {
        return;
    }*/

    bootbox.confirm("确认修改该条费率吗", function (result) {
        if (result) {
            awardUpdateForm.ajaxSubmit(function(data){
                bootbox.setDefaults("locale", "zh_CN");
                bootbox.alert(data.body, function(){
                    if(data.statusCode == "OK"){
                        location.reload();
                    }
                });
            });
        }
    });
}
