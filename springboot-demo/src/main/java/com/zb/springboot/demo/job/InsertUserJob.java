package com.zb.springboot.demo.job;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
@Slf4j
public class InsertUserJob implements Job {

    @Resource
    private UserService userService;
    // Job对象不是单例
    private static final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        User user = new User();
        user.setUsername("name" + count.getAndIncrement());
        user.setAge(20);
        user.setCreateTime(new Date());
        log.info("insert username: {}, {}", user.getUsername(), this);
        userService.insert(user);
    }
}
