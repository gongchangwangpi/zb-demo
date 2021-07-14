package com.ext.demo;

import com.ext.BizScenario;
import com.ext.Extension;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
@Slf4j
@Extension(bizId = "2")
public class UserService2ExtPt implements UserServiceExtPt {

    @Override
    public String getName(Long id) {
        log.info("=====>>> biz = 2, id = {}", id);
        return "name" + id;
    }

    @Override
    public int getAge(BizScenario bizScenario, Long id) {
        log.info("=====>>> biz = 2, id = {}", id);
        return id.intValue();
    }
}
