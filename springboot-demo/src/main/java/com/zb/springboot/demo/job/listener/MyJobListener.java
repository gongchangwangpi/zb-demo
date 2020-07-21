package com.zb.springboot.demo.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * @author zhangbo
 * @date 2020/7/20
 */
@Slf4j
public class MyJobListener extends JobListenerSupport {

    @Override
    public String getName() {
        return "MyTest-JobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info(" === jobToBeExecuted ");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info(" ===<<< jobExecutionVetoed ");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info(" ===>>> jobWasExecuted, jobException = {}", jobException == null ? "" : jobException.getMessage());
    }
}
