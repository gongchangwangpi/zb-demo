package com.zb.fund.service.fund;

import com.zb.fund.domain.FundType;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
public interface FundTypeService {

    void save(FundType fundType);

    void update(FundType fundType);

    FundType get(String fundTypeCode);

    List<FundType> list();

}
