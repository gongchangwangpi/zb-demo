package com.zb.fund.service.fund;

import com.github.pagehelper.PageInfo;
import com.zb.fund.domain.FundTrend;
import com.zb.fund.domain.query.FundTrendQuery;

import java.util.Date;

/**
 * @author zhangbo
 */
public interface FundTrendService {

    /**
     * 保存数据
     * @param statDate
     */
    void saveFromEastMoney(Date statDate);

    PageInfo<FundTrend> pageList(FundTrendQuery query);
}
