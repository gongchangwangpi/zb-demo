package com.zb.springboot.demo;

import com.zb.springboot.demo.service.user.CglibTransactionRollbackUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangbo
 * @date 2020/8/3
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class CglibTransactionRollbackUserServiceTest {

    @Resource
    private CglibTransactionRollbackUserService cglibTransactionRollbackUserService;

    @Test
    @Rollback(value = false)
    public void testSave() {
        cglibTransactionRollbackUserService.txTest();
    }

}
