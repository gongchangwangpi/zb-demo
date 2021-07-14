package com.db.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.lock.entity.DistributionLockEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangbo
 * @date 2020/12/31
 */
@Mapper
public interface DistributionLockMapper extends BaseMapper<DistributionLockEntity> {
}
