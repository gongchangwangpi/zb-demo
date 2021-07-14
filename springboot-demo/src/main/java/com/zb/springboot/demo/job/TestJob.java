package com.zb.springboot.demo.job;

import com.zb.springboot.demo.service.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
@Slf4j
public class TestJob implements Job {

    static long time = System.currentTimeMillis();

    @Resource
    private UserService userService;
    // Job对象不是单例
    private static final AtomicInteger count = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("=====>>> test job run ... {}", System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
//        TimeUnit.SECONDS.sleep(10);
//        log.info("=====>>> test job run end... ");

    }
}
