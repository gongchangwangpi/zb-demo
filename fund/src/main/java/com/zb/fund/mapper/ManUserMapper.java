package com.zb.fund.mapper;


import com.zb.fund.domain.ManUser;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
public interface ManUserMapper {

    int insert(ManUser manUser);
    int update(ManUser manUser);
    List<ManUser> selectList();
    ManUser selectById(Long id);
}
