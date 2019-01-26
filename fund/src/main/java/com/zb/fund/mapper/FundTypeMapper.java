package com.zb.fund.mapper;

import com.zb.fund.domain.FundType;

import java.util.List;

public interface FundTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FundType record);

    int update(FundType record);

    FundType selectByCode(String fundTypeCode);

    List<FundType> selectList();
}