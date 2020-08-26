package com.job.quartz;

import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
public class JobUtil {

    public static final String JOB_NAME = "jobName";
    public static final String JOB_GROUP = "jobGroup";
    public static final String JOB_DESC = "echo job test";
    public static final String JOB_DATA_TYPE = "type";

    public static JobKey jobKey() {
        return JobKey.jobKey(JOB_NAME, JOB_GROUP);
    }

    public static TriggerKey triggerKey() {
        return TriggerKey.triggerKey(JOB_NAME, JOB_GROUP);
    }

    public static JobDetail jobDetail() {
        return JobBuilder.newJob(EchoJob.class)
                .withIdentity(jobKey())
                .withDescription(JOB_DESC)
                .build();
    }

    public static JobDataMap jobDataMap(String value) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_DATA_TYPE, value);
        return jobDataMap;
    }

    public static Scheduler scheduler() throws SchedulerException {
        DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory.getInstance();
        // 可设置job持久化 JobStore
        schedulerFactory.createScheduler(new SimpleThreadPool(2, 5), new RAMJobStore());
        return schedulerFactory.getScheduler();
    }
}
