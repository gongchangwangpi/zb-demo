package com.zb.fund.mapper;

import com.zb.fund.domain.FundTrend;

public interface FundTrendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundTrend record);

    int insertSelective(FundTrend record);

    FundTrend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundTrend record);

    int updateByPrimaryKey(FundTrend record);
}