package com.jdksource.util.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ctl 控制位，共32位，其中低29位表示当前线程数，高3位表示线程池状态
 * 
 * SHUTDOWN     000
 * STOP         001
 * TIDYING      010
 * TERMINATED   011
 * RUNNING      111
 * 
 * @author zhangbo
 */
public class ThreadPoolExecutorTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;

    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    
    private static final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }

    /*
     * Bit field accessors that don't require unpacking ctl.
     * These depend on the bit layout and on workerCount being never negative.
     */

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    public static void main(String[] args) {
        int ctl = ThreadPoolExecutorTest.ctl.get();

        System.out.println("COUNT_BITS \t= \t" + Integer.toBinaryString(COUNT_BITS));
        System.out.println("CAPACITY \t= \t" + Integer.toBinaryString(CAPACITY));
        System.out.println("RUNNING \t= \t" + Integer.toBinaryString(RUNNING));
        System.out.println("SHUTDOWN \t= \t" + Integer.toBinaryString(SHUTDOWN));
        System.out.println("STOP \t\t= \t" + Integer.toBinaryString(STOP));
        System.out.println("TIDYING \t= \t" + Integer.toBinaryString(TIDYING));
        System.out.println("TERMINATED \t= \t" + Integer.toBinaryString(TERMINATED));
        
        System.out.println("ctl \t\t= \t" + Integer.toBinaryString(ctl));
        System.out.println("isRunning \t= \t" + isRunning(ctl));
        System.out.println("workCount \t= \t" + workerCountOf(ctl + 1));

        System.out.println("------------------------------------------");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(4),
                new DefaultThreadFactory("pool"),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 15; i++) {
            executor.execute(new Job(i));
        }
        
        executor.shutdown();
    }
    
    @Slf4j
    private static class Job implements Runnable {
        int i ;

        public Job(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            log.info(" ------ run {}", i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(" ====== end {}", i);
        }
    }
    
}
