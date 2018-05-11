var orderChart;
$(function () {
    // 基于准备好的dom，初始化echarts实例
    orderChart = echarts.init(document.getElementById('orderCharts'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '车险订单趋势'
        },
        tooltip: {},
        legend: {
            data: ['新增订单', '支付订单']
        },
        xAxis: {
            boundaryGap: false,
            data: [1020, 1021, 1022, 1023, 1024]
        },
        yAxis: {},
        series: [
            {
                name: '新增订单',
                type: 'line',
                data: [11, 120, 50, 90, 20],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: '支付订单',
                type: 'line',
                data: [10, 100, 22, 87, 5],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    orderChart.setOption(option);

    getOrderData();
});

function setShowType(type) {
    var option = orderChart.getOption();
    if (type == 1) {
        // 不推荐这样直接修改option的值
        option.series[0].type = "line";
        option.series[1].type = "line";
        orderChart.setOption(option);
    } else if (type == 2) {
        option.series[0].type = "bar";
        option.series[1].type = "bar";
        orderChart.setOption(option);
    }
}

function getOrderData(beginDate, engDate) {
    $.get("/echarts/getOrderData", {beginDate: beginDate, engDate: engDate}, function (response) {
        if (response) {
            orderChart.setOption({
                xAxis: {
                    data: response.xaxis
                },
                series: [
                    {
                        data: response.createCount
                    },
                    {
                        data: response.payCount
                    }
                ]
            });
        }
    });
}