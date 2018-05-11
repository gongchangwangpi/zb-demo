package com.jdksource.util.concurrent;

import java.util.concurrent.*;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Callable}被包装成{@link FutureTask},Future是实现了{@link Runnable}和{@link Future}的,
 * 所以最终在线程池{@link ThreadPoolExecutor#submit(Callable)}的时候,底层还是调用的
 * {@link FutureTask#run()},在run()里面在调用{@link Callable#call()},
 * 然后在设置call()的返回值或者异常给{@link FutureTask#outcome},
 * 最后在调用{@link Future#get()}的时候,返回{@link FutureTask#outcome}
 * 
 * @author zhangbo
 */
@Slf4j
public class ThreadPoolCallableTest {

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 5000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>(8));

        Future<String> future = threadPoolExecutor.submit(new Call());

        String result = future.get();
        
        log.info(result);

        threadPoolExecutor.shutdown();
    }
    
    static class Call implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("call....");
            long id = Thread.currentThread().getId();
            return "call : " + id;
        }
    }
}
