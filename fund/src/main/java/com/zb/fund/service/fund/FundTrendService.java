package com.zb.fund.service.fund;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zb.fund.domain.FundTrend;
import com.zb.fund.domain.query.FundTrendQuery;

import java.util.Date;
import java.util.List;

/**
 * @author zhangbo
 */
public interface FundTrendService {

    /**
     * 保存数据
     * @param statDate
     */
    void saveFromEastMoney(Date statDate);

    Page<FundTrend> pageList(FundTrendQuery query);
}
