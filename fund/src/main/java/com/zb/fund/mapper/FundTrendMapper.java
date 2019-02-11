package com.zb.fund.mapper;

import com.zb.fund.domain.FundTrend;
import com.zb.fund.domain.query.FundTrendQuery;

import java.util.Date;
import java.util.List;

public interface FundTrendMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(FundTrend record);

    FundTrend selectByPrimaryKey(Long id);

    int update(FundTrend record);

    int batchInsert(List<FundTrend> trendList);

    List<FundTrend> selectList(FundTrendQuery query);

    Date selectLatestStatisticsDate();
}