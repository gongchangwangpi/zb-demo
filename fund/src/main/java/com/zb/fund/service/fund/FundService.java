package com.zb.fund.service.fund;

import com.github.pagehelper.PageInfo;
import com.zb.fund.domain.Fund;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
public interface FundService {

    long save(Fund fund);

    int update(Fund fund);

    Fund get(String fundCode);

    List<Fund> list();

    PageInfo<Fund> pageList();

}
