package com.zb.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.job.InsertUserJob;
import com.zb.springboot.demo.job.TestJob;
import com.zb.springboot.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * Springboot 启动类
 *
 * @author zhangbo
 */
@Slf4j
@EnableAsync
@ServletComponentScan(basePackages = "com.zb.springboot.demo.servlet")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan(value = "com.zb.springboot.demo.mapper")
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) throws SchedulerException {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir"));
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);

//        MailSenderUtil mailSenderUtil = context.getBean(MailSenderUtil.class);
//        mailSenderUtil.sendSimpleMail();

//        SchedulerFactoryBean schedulerFactoryBean = context.getBean(SchedulerFactoryBean.class);
//        Scheduler scheduler = schedulerFactoryBean.getObject();
//        scheduler.scheduleJob(getJobDetail(jobdatamap()), getTrigger());
//        System.out.println(" ----------------- scheduler");

//        Scheduler scheduler2 = schedulerFactoryBean.getObject();
//        scheduler2.scheduleJob(getJobDetail2(jobdatamap2()), getTrigger2());
//        System.out.println(" ----------------- scheduler");

    }

    public static Trigger getTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity("InsertUser", "User")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/15 * * * * ?"))
                .build();
    }

    public static Trigger getTrigger2() {
        return TriggerBuilder.newTrigger()
                .withIdentity("TestJob", "User")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/20 * * * * ?"))
                .build();
    }

    public static JobDetail getJobDetail(JobDataMap map) {
        return JobBuilder.newJob(InsertUserJob.class).withIdentity(new JobKey("InsertUser")).withDescription("插入用户").setJobData(map).build();
    }

    public static JobDetail getJobDetail2(JobDataMap map) {
        return JobBuilder.newJob(TestJob.class).withIdentity(new JobKey("TestJob")).withDescription("TestJob").setJobData(map).build();
    }

    private static JobDataMap jobdatamap() {
        JobDataMap map = new JobDataMap();
        map.put("name", "InsertUser");
        map.put("jobGroup", "User");
        map.put("cronExpression", "*/30 * * * * ?");
        map.put("parameter", null);
        map.put("jobDescription", "插入用户");
        map.put("vmParam", null);
        map.put("jarPath", "");
        map.put("status", "OPEN");
        return map;
    }

    private static JobDataMap jobdatamap2() {
        JobDataMap map = new JobDataMap();
        map.put("name", "TestJob");
        map.put("jobGroup", "TestJob");
        map.put("cronExpression", "*/15 * * * * ?");
        map.put("parameter", null);
        map.put("jobDescription", "TestJob");
        map.put("vmParam", null);
        map.put("jarPath", "");
        map.put("status", "OPEN");
        return map;
    }
}
