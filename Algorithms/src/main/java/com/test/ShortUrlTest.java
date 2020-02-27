package com.test;

import com.util.SimpleHttpClient;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 * @date 2020-01-20
 */
public class ShortUrlTest {

    private static final String longUrl9 = "http://127.0.0.1:9099/api/short?longUrl=https://baidu.com/s?wd=短连接";
    private static final String longUrl8 = "http://127.0.0.1:9098/api/short?longUrl=https://baidu.com/s?wd=短连接";
    private static final String longUrl7 = "http://127.0.0.1:9097/api/short?longUrl=https://baidu.com/s?wd=短连接";
    private static final String[] longUrls = {longUrl7, longUrl8, longUrl9};

    private static final String appKey = "app-test";

    public static void main(String[] args) throws InterruptedException {
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.setLogLevel(SimpleHttpClient.LogLevel.NONE);
        simpleHttpClient.init();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("appKey", appKey);

        ExecutorService executorService = Executors.newFixedThreadPool(64);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        long start = System.currentTimeMillis();

        int totalTest = 50000;
        int maxTest = totalTest - 1;

        for (int i = 0; i < totalTest; i++) {
            int idx = i;
            executorService.submit(() -> {
                simpleHttpClient.getByHeaders(longUrls[idx % 3], headers);
                if (idx == maxTest) {
                    countDownLatch.countDown();
                }
            });
        }

        executorService.shutdown();
        countDownLatch.await();
        long end = System.currentTimeMillis();

        System.out.println("use time: " + (end - start) + ", total req: " + totalTest);
        System.out.println("use time per req: " + (((double) (end - start)) / totalTest));
//        use time: 289877, total req: 50000
//        use time per req: 5.79754
//        use time: 249930, total req: 50000
//        use time per req: 4.9986
//        use time: 211518, total req: 50000
//        use time per req: 4.23036
//        use time: 249299, total req: 50000
//        use time per req: 4.98598
//        use time: 239525, total req: 50000
//        use time per req: 4.7905
    }

}
