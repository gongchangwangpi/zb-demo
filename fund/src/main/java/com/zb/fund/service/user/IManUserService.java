package com.zb.fund.service.user;


import com.zb.fund.domain.ManUser;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
public interface IManUserService {

    int save(ManUser manUser);
    int update(ManUser manUser);
    List<ManUser> query();
    ManUser get(Long id);

}
