package com.zb.springboot.demo.service.transaction;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.TxMapper;
import com.zb.springboot.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author bo6.zhang
 * @date 2021/3/5
 */
@Slf4j
@Service
public class TxIsolationService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TxMapper txMapper;

    @Transactional(isolation = Isolation.DEFAULT)
    public User defaultIso() {
        logIsolation("defaultIso");
        return userMapper.selectByUserId(1L);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public User readUncommitted() {
        logIsolation("readUncommitted");
        return userMapper.selectByUserId(1L);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User readCommitted() {
        logIsolation("readCommitted");
        return userMapper.selectByUserId(1L);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User repeatableRead() {
        logIsolation("repeatableRead");
        return userMapper.selectByUserId(1L);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User serializable() {
        logIsolation("serializable");
        return userMapper.selectByUserId(1L);
    }

    private void logIsolation(String method) {
        log.info(" ====>>>> method: {} <<<<====", method);
        log.info(" ====>>>> showGlobalIsolation: {}", txMapper.showGlobalIsolation());
        log.info(" ====>>>> showSessionIsolation: {}", txMapper.showSessionIsolation());
    }
}
