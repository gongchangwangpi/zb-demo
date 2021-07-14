package com.zb.springboot.demo.job;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.quartz.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
@Slf4j
public class InsertUserJob implements Job {

    static long time = System.currentTimeMillis();

    @Resource
    private UserService userService;
    // Job对象不是单例
    private static final AtomicInteger count = new AtomicInteger(100);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        if (count.getAndIncrement() % 2 == 0) {
//            int i = 1 / 0;
//        }
        Date previousFireTime = context.getPreviousFireTime();
        Date nextFireTime = context.getNextFireTime();
        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
//        SimpleTrigger trigger = (SimpleTrigger) context.getTrigger();

        log.info("=====>>> InsertUserJob job run ... {}", System.currentTimeMillis() - time);
        time = System.currentTimeMillis();

//        log.info("previousFireTime = {}, nextFireTime = {}, interval = {}", previousFireTime == null ? "" : dateFormat.format(previousFireTime), dateFormat.format(nextFireTime)/*, trigger.getRepeatInterval()*/);

//        User user = new User();
//        user.setUsername("name" + count.getAndIncrement());
//        user.setAge(20);
//        user.setCreateTime(new Date());
//        log.info("insert username: {}, {}", user.getUsername(), this);
//        userService.insert(user);
    }
}
