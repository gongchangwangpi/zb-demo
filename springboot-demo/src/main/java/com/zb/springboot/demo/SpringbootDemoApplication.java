package com.zb.springboot.demo;

import com.zb.springboot.demo.job.InsertUserJob;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
@MapperScan(value = "com.zb.springboot.demo.mapper")
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) throws SchedulerException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);

//        MailSenderUtil mailSenderUtil = context.getBean(MailSenderUtil.class);
//
//        mailSenderUtil.sendSimpleMail();

//        SchedulerFactoryBean schedulerFactoryBean = context.getBean(SchedulerFactoryBean.class);
//
//        Scheduler scheduler = schedulerFactoryBean.getObject();
//
//        scheduler.scheduleJob(getJobDetail(jobDataMap()), getTrigger());

        System.out.println(" ----------------- scheduler");
    }

    public static Trigger getTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity("InsertUser", "User")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                .build();
    }

    public static JobDetail getJobDetail(JobDataMap map) {
        return JobBuilder.newJob(InsertUserJob.class).withIdentity(new JobKey("InsertUser")).withDescription("插入用户").setJobData(map).build();
    }

    private static JobDataMap jobDataMap() {
        JobDataMap map = new JobDataMap();
        map.put("name", "InsertUser");
        map.put("jobGroup", "User");
        map.put("cronExpression", "*/10 * * * * ?");
        map.put("parameter", null);
        map.put("jobDescription", "插入用户");
        map.put("vmParam", null);
        map.put("jarPath", "");
        map.put("status", "OPEN");
        return map;
    }
}
