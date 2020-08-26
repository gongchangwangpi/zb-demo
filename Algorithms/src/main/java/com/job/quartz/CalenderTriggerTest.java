package com.job.quartz;

import org.quartz.*;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
public class CalenderTriggerTest {

    public static void main(String[] args) throws SchedulerException {

        CalendarIntervalScheduleBuilder calendarIntervalScheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInSeconds(5);

        Trigger trigger = TriggerBuilder.newTrigger()
                .usingJobData(JobUtil.jobDataMap("calenderInterval"))
                .withIdentity(JobUtil.triggerKey())
                .withSchedule(calendarIntervalScheduleBuilder)
                .withDescription(JobUtil.JOB_DESC)
                .build();

        Scheduler scheduler = JobUtil.scheduler();

        scheduler.scheduleJob(JobUtil.jobDetail(), trigger);

        scheduler.start();
    }

}
