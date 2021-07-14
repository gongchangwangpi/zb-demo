package com.db.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2020/12/31
 */
@Slf4j
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class LockApplication {

    public static void main(String[] args) throws Exception {

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LockConfig.class);

        ConfigurableApplicationContext context = SpringApplication.run(LockApplication.class, args);

        DistributionLockUtil lockUtil = context.getBean(DistributionLockUtil.class);

        log.info("lockUtil = {}", lockUtil);

        testReenter(lockUtil);

    }

    private static void testReenter(DistributionLockUtil lockUtil) throws InterruptedException {
        DistributionLockNameEnum lockNameEnum = DistributionLockNameEnum.ORDER;
        String lockValue = UUID.randomUUID().toString();
        boolean success = lockUtil.tryLock(lockNameEnum, lockValue, 10, TimeUnit.SECONDS);
        if (success) {
            TimeUnit.SECONDS.sleep(5);
            success = lockUtil.tryLock(lockNameEnum, lockValue, 10, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(5);
        }

        lockUtil.unlock(lockNameEnum, lockValue);
        lockUtil.unlock(lockNameEnum, lockValue);
    }

}
