package com.invest.mapper;

import com.invest.entity.MarketTransDataDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 市场交易数据 Mapper 接口
 * </p>
 *
 * @author zhangbo
 * @since 2021-07-14
 */
public interface MarketTransDataMapper extends BaseMapper<MarketTransDataDO> {

    int batchInsert(List<MarketTransDataDO> list);
}
