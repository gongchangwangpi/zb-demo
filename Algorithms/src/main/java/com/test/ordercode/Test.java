package com.test.ordercode;

import com.zb.demo.util.number.SnowflakeIdWorker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 *                  lockByTime     syncByTime    byAtomicLong          AtomicLongAdd          SnowflakeId
 * 4thread/10秒   795146/5476614                5530349/23618652      2646290/21226440      3726497/14232791
 * 8thread/10秒   795146/5476614                3145729/21285522      2646290/21226440      
 * 16thread/10秒  330000/5435544                1572865/24214513      1572870/22190070
 * 32thread/10秒  185653/5739315                 786433/19812399       786440/16888840
 * 
 * @author zhangbo
 */
public class Test {

    static long time = 1000 * 10;
    static int threadCount = 4;

    public static void main(String[] args) throws InterruptedException {
        
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Set<Object>> codeSet = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            Set<Object> set = new HashSet<>();
            codeSet.add(set);
            executorService.execute(new Coder(set, countDownLatch));
        }

        countDownLatch.await();
        executorService.shutdown();
        
        Long count = codeSet.stream().collect(Collectors.summingLong(Set::size));

        System.out.println("------ 总生成订单：" + count);
    }
    
    static class Coder implements Runnable {
        Set<Object> set;
        CountDownLatch countDownLatch;
        public Coder(Set<Object> set, CountDownLatch countDownLatch) {
            this.set = set;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            
            long start = System.currentTimeMillis();

            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1L, 1L);

            while ((System.currentTimeMillis() - start) < time) {
//                String code = GenerateOrderCode.lockByTime();
//                if (!set.add(code)) {
//                    throw new RuntimeException("订单号重复：" + code);
//                }
                
//                long code = GenerateOrderCode.orderCodeByAtomicLong();
//                if (!set.add(code)) {
//                    throw new RuntimeException("订单号重复：" + code);
//                }
                
//                long code = GenerateOrderCode.orderCodeByAtomicLong(20);
//                for (int i = 0; i < 10; i++) {
//                    if (!set.add(code + i)) {
//                        throw new RuntimeException("订单号重复：" + code);
//                    }
//                }

                long id = idWorker.nextId();
                if (!set.add(id)) {
                    throw new RuntimeException("订单号重复：" + id);
                }

            }
            
            System.out.println(set.size());
            countDownLatch.countDown();
        }
    }
    
}
