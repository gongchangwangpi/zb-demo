

// 查看批单日志
function showEndorLogs(policyLoanApplyId) {
    bootbox.dialog({
        size : "modal-lg",
        title : "批单详情",
        message : msg,
        buttons : {
            "cancel" : {
                "label" : "<i class='icon-info'></i> 关闭",
                "className" : "btn-sm btn-danger",
                "callback" : function() {
                }
            }
        }
    });
}

var msg = '<div class="row"><div class="col-xs-12"><table width="100%" class="table table-striped table-bordered table-hover" style="font-size: 12px"><thead><tr><th>时间</th><th>险种</th><th>批单类型</th><th>批单号</th><th>原保费</th><th>变化保费</th><th>现保费</th></tr></thead><tbody><tr><td>2018-06-12 17:37:35</td><td>商业险</td><td>批增</td><td>13021000074108122018000024</td><td>3195.24</td><td>410.29</td><td>3605.53</td></tr></tbody></table></div></div>';

function getMessage(logs) {
    var len = logs.length;
    var tbody = '';
    for(var i = 0; i < len; i++) {
        var log = logs[i];
        tbody += '<tr><td>'+log.operTime+'</td><td>'+log.endorseIns+'</td><td>'+log.endorseCat+'</td><td>'+log.endorseNo
            +'</td><td>'+log.originalPremium+'</td><td>'+log.endorsePremiumChange+'</td><td>'+log.newPremium+'</td></tr>';
    }
    if (!tbody) {
        tbody = '<tr><td colspan="7">没有查询到该保单的批单记录</td></tr>';
    }
    return dialogForm1 + tbody + dialogForm2;
}


var dialogForm1 = '<div class="row">'
    + '<div class="col-xs-12">'
    + '<table width="100%" class="table table-striped table-bordered table-hover" style="font-size: 12px">'
    + '<thead><tr><th>时间</th><th>险种</th><th>批单类型</th><th>批单号</th><th>原保费</th><th>变化保费</th><th>现保费</th>'
    + '</tr></thead><tbody>';
var dialogForm2 = '</tbody></table></div></div>';