package com.job.quartz;

import org.quartz.*;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
public class CronTriggerTest {

    public static void main(String[] args) throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .usingJobData(JobUtil.jobDataMap("cron"))
                .withIdentity(JobUtil.triggerKey())
                .withSchedule(cronScheduleBuilder)
                .withDescription(JobUtil.JOB_DESC)
                .build();

        Scheduler scheduler = JobUtil.scheduler();

        scheduler.scheduleJob(JobUtil.jobDetail(), trigger);

        scheduler.start();
    }

}
