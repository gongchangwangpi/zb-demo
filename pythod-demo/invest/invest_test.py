#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/4/2 20:18:32

# 语言：python
# 工具：Ricequant米筐量化交易平台
# 可以自己import我们平台支持的第三方python模块，比如pandas、numpy等。
from venv import logger

import numpy as np

# 在这个方法中编写任何的初始化逻辑。context对象将会在你的算法策略的任何方法之间做传递。
def init(context):
    # 查询revenue前十名的公司的股票并且他们的pe_ratio在55和60之间。打fundamentals的时候会有auto-complete方便写查询代码。
    fundamental_df = get_fundamentals(
        query(
            fundamentals.income_statement.revenue, fundamentals.eod_derivative_indicator.pe_ratio
        ).filter(
            fundamentals.eod_derivative_indicator.pe_ratio > 55
        ).filter(
            fundamentals.eod_derivative_indicator.pe_ratio < 60
        ).order_by(
            fundamentals.income_statement.revenue.desc()
        ).limit(
            10
        )
    )

    # 将查询结果dataframe的fundamental_df存放在context里面以备后面只需：
    context.fundamental_df = fundamental_df

    # 实时打印日志看下查询结果，会有我们精心处理的数据表格显示：
    logger.info(context.fundamental_df)
    update_universe(context.fundamental_df.columns.values)

    # 对于每一个股票按照平均现金买入：
    context.stocks = context.fundamental_df.columns.values
    stocks_number = len(context.stocks)
    context.average_percent = 0.99 / stocks_number
    logger.info("Calculated average percent for each stock is: %f" % context.average_percent)
    context.fired = False


# 你选择的证券的数据更新将会触发此段逻辑，例如日或分钟历史数据切片或者是实时数据切片更新
def handle_bar(context, bar_dict):
    # 开始编写你的主要的算法逻辑

    # bar_dict[order_book_id] 可以拿到某个证券的bar信息
    # context.portfolio 可以拿到现在的投资组合状态信息

    # 使用order_shares(id_or_ins, amount)方法进行落单

    # TODO: 开始编写你的算法吧！

    # 对于选择出来的股票按照平均比例买入：
    if not context.fired:
        for stock in context.stocks:
            order_target_percent(stock, context.average_percent)
            logger.info("Bought: " + str(context.average_percent) + " % for stock: " + str(stock))
        context.fired = True