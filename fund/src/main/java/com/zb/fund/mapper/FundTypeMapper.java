package com.zb.fund.mapper;

import com.zb.fund.domain.FundType;

public interface FundTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FundType record);

    int insertSelective(FundType record);

    FundType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundType record);

    int updateByPrimaryKey(FundType record);
}