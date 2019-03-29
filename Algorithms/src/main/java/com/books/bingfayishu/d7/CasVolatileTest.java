package com.books.bingfayishu.d7;

import com.zb.demo.util.unsafe.UnsafeUtil;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 */
public class CasVolatileTest {

    private volatile int volatileValue = 0;
    private volatile int value = 0;
    
    private static Unsafe unsafe = UnsafeUtil.getUnsafe();

    private static final long valueOffset;
    
    static {
        try {
            valueOffset = unsafe.objectFieldOffset(CasVolatileTest.class.getDeclaredField("volatileValue"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {

        int threadCount = 16;
        int addCount = 10_000;
        
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CasVolatileTest test = new CasVolatileTest();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i1 = 0; i1 < threadCount; i1++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i2 = 0; i2 < addCount; i2++) {
                        test.casAdd();
                        test.add();
                    }
                    countDownLatch.countDown();
                }
            });
        }

        executorService.shutdown();
        // 等待线程执行完毕
        countDownLatch.await();
        
        System.out.println("volatileValue = " + test.volatileValue);
        System.out.println("value = " + test.value);
    }
    
    private void casAdd() {
        while (!unsafe.compareAndSwapInt(this, valueOffset, volatileValue, volatileValue + 1));
    }
    
    private void add() {
        value++;
    }
}
