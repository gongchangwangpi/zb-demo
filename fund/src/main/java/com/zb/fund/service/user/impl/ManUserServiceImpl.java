package com.zb.fund.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.zb.fund.domain.ManUser;
import com.zb.fund.mapper.ManUserMapper;
import com.zb.fund.service.user.IManUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
@Slf4j
@Service
public class ManUserServiceImpl implements IManUserService {

    @Autowired
    private ManUserMapper manUserMapper;

    @Override
    public int save(ManUser manUser) {
        return manUserMapper.insert(manUser);
    }

    @Override
    public int update(ManUser manUser) {
        return manUserMapper.update(manUser);
    }

    @Override
    public List<ManUser> query() {
        return manUserMapper.selectList();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ManUser get(Long id) {
        ManUser manUser = manUserMapper.selectById(id);
        System.out.println(JSON.toJSONString(manUser));

//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            log.error("InterruptedException", e);
//        }
//
//        manUser = manUserMapper.selectById(id);
//        System.out.println(JSON.toJSONString(manUser));
        return manUser;
    }
}
