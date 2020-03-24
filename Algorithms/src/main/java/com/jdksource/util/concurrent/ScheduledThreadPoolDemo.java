package com.jdksource.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * ScheduledThreadPool原理
 *
 * 该线程池的任务队列是{@link ScheduledThreadPoolExecutor.DelayedWorkQueue}延迟队列，
 * 里面的任务默认会根据下一次要执行的时间排序{@link ScheduledThreadPoolExecutor.ScheduledFutureTask#compareTo(java.util.concurrent.Delayed)}，
 * worker每次在获取任务时，通过{@link ScheduledThreadPoolExecutor.DelayedWorkQueue#take()}进行阻塞，
 * 直到达到任务的执行时间
 *
 * 我们提交的任务，会包装为{@link ScheduledThreadPoolExecutor.ScheduledFutureTask}，
 * 里面重写了run(),在该run()里面调用我们自己的run，然后在重置下一次任务的时间，然后重新添加到workQueue
 *  public void run() {
 *      boolean periodic = isPeriodic();
 *      if (!canRunInCurrentRunState(periodic))
 *          cancel(false);
 *      else if (!periodic)
 *          ScheduledFutureTask.super.run();
 *      else if (ScheduledFutureTask.super.runAndReset()) {
 *          setNextRunTime();
 *          reExecutePeriodic(outerTask);
 *      }
 *  }
 *
 * @author zhangbo
 * @date 2020/3/24
 **/
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("ScheduledThreadPool");
        }, 1, 5, TimeUnit.SECONDS);

    }

}
