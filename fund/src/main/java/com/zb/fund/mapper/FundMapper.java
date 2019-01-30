package com.zb.fund.mapper;

import com.zb.fund.domain.Fund;

import java.util.List;

public interface FundMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fund record);

    Fund selectByPrimaryKey(Long id);

    int update(Fund record);

    List<String> selectFundCodeList();

    int batchInsert(List<Fund> fundList);
}