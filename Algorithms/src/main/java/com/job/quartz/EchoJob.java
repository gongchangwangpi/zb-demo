package com.job.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/8/26
 */
@Slf4j
public class EchoJob implements Job {

    private static final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date previousFireTime = jobExecutionContext.getPreviousFireTime();
        Date nextFireTime = jobExecutionContext.getNextFireTime();
        long jobRunTime = jobExecutionContext.getJobRunTime();

        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        log.info("type = {}, execute count {}", jobDataMap.get(JobUtil.JOB_DATA_TYPE), count.getAndIncrement());
        log.info("previousFireTime = {}, nextFireTime = {}, jobRunTime = {}",
                previousFireTime == null ? null : dateFormat.format(previousFireTime),
                dateFormat.format(nextFireTime), jobRunTime);
    }
}
