package com.books.bingfayishu.d7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author zhangbo
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) throws InterruptedException {

        User zhangsan = new User("zhangsan", 0, 0);

        int threadCount = 16;
        int addCount = 10_000;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i1 = 0; i1 < threadCount; i1++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i2 = 0; i2 < addCount; i2++) {
                        updater.getAndIncrement(zhangsan);
                        zhangsan.setAge(zhangsan.getAge() + 1);
                    }
                    countDownLatch.countDown();
                }
            });
        }

        executorService.shutdown();
        // 等待线程执行完毕
        countDownLatch.await();

        System.out.println("volatileAge = " + zhangsan.getVolatileAge());
        System.out.println("age = " + zhangsan.getAge());
        
    }
    
    private static final AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "volatileAge");
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        public volatile int volatileAge;
        public volatile int age;
    }
}
