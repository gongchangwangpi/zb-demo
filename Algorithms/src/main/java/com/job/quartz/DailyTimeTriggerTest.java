package com.job.quartz;

import org.quartz.*;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
public class DailyTimeTriggerTest {

    public static void main(String[] args) throws SchedulerException {
        DailyTimeIntervalScheduleBuilder dailyTimeIntervalScheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule();
        dailyTimeIntervalScheduleBuilder.withIntervalInSeconds(5);

        Trigger trigger = TriggerBuilder.newTrigger()
                .usingJobData(JobUtil.jobDataMap("dailyTime"))
                .withIdentity(JobUtil.triggerKey())
                .withSchedule(dailyTimeIntervalScheduleBuilder)
                .withDescription(JobUtil.JOB_DESC)
                .build();

        Scheduler scheduler = JobUtil.scheduler();

        scheduler.scheduleJob(JobUtil.jobDetail(), trigger);

        scheduler.start();
    }

}
