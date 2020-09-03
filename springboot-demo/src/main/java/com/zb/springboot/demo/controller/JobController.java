package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.dto.JobDto;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangbo
 * @date 2020/4/24
 */
@Slf4j
@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Resource
    private Scheduler scheduler;

    @PostMapping(value = "/add/simple")
    public String addSimpleJob(@RequestBody JobDto jobDto) throws Exception {
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobDto.getJobClassName()))
                .withIdentity(JobKey.jobKey(jobDto.getJobClassName(), jobDto.getJobGroupName()))
                .withDescription(jobDto.getJobDescription())
                .build();

        // 表达式调度构建器(即任务执行的时间)
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(jobDto.getMinutes());

        // 按新的cronExpression表达式构建一个新的trigger
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(jobDto.getJobClassName(), jobDto.getJobGroupName()))
                .withSchedule(scheduleBuilder)
                .withDescription(jobDto.getJobDescription())
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Add job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        } catch (SchedulerException e) {
            log.error("Add job error", e);
            throw new Exception("add job error");
        }
        return "OK";
    }

    @PostMapping(value = "/add")
    public String addJob(@RequestBody JobDto jobDto) throws Exception {
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobDto.getJobClassName()))
                .withIdentity(jobDto.getJobClassName(), jobDto.getJobGroupName())
                .withDescription(jobDto.getJobDescription())
                .build();

        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDto.getCronExpression());

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobDto.getJobClassName(), jobDto.getJobGroupName())
                .withSchedule(scheduleBuilder)
                .withDescription(jobDto.getJobDescription())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Add job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        } catch (SchedulerException e) {
            log.error("Add job error", e);
            throw new Exception("add job error");
        }
        return "OK";
    }

    @PostMapping(value = "/pause")
    public String pauseJob(@RequestBody JobDto jobDto) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobDto.getJobClassName(), jobDto.getJobGroupName()));
        log.info("Pause job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        return "OK";
    }

    @PostMapping(value = "/resume")
    public String resumeJob(@RequestBody JobDto jobDto) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobDto.getJobClassName(), jobDto.getJobGroupName()));
        log.info("Resume job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        return "OK";
    }

    @PostMapping(value = "/reschedule")
    public String rescheduleJob(@RequestBody JobDto jobDto) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobDto.getJobClassName(), jobDto.getJobGroupName());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDto.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info("Reschedule job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        } catch (SchedulerException e) {
            log.error("Reschedule job error", e);
            throw new Exception("Reschedule job error");
        }
        return "OK";
    }

    @PostMapping(value = "/delete")
    public String deleteJob(@RequestBody JobDto jobDto) throws Exception {
        String jobClassName = jobDto.getJobClassName();
        String jobGroupName = jobDto.getJobGroupName();

        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        log.info("Delete job {}-{} success", jobDto.getJobClassName(), jobDto.getJobGroupName());
        return "OK";
    }

    private static Class<? extends Job> getClass(String className) throws Exception {
        Class<?> clz = Class.forName(className);
        if (Job.class.isAssignableFrom(clz)) {
            return (Class<? extends Job>) clz;
        }
        String msg = className + " must implements org.quartz.Job";
        log.error(msg);
        throw new IllegalArgumentException(msg);
    }
}
