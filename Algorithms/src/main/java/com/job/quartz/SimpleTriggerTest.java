package com.job.quartz;

import org.quartz.*;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
public class SimpleTriggerTest {

    public static void main(String[] args) throws SchedulerException {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(5);

        Trigger trigger = TriggerBuilder.newTrigger()
                .usingJobData(JobUtil.jobDataMap("simple"))
                .withIdentity(JobUtil.triggerKey())
                .withSchedule(simpleScheduleBuilder)
                .withDescription(JobUtil.JOB_DESC)
                .build();

        Scheduler scheduler = JobUtil.scheduler();

        scheduler.scheduleJob(JobUtil.jobDetail(), trigger);

        scheduler.start();
    }

}
