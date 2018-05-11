package com.spring.ratelimit;

import com.util.SimpleHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟HTTP请求，瞬间流量
 * 
 * Created by books on 2017/12/21.
 */
public class HttpTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        SimpleHttpClient httpClient = new SimpleHttpClient();
        httpClient.init();
        
        for (int i = 0; i < 20; i++) {
            
            if (i % 2 == 1) {
                executorService.execute(() -> {
                    String res = httpClient.get("http://localhost/limit2");
                    System.out.println(res + "\t" + System.currentTimeMillis());
                });
                
            } else {
                
                executorService.execute(() -> {
                    String res = httpClient.get("http://localhost/limit1");
                    System.out.println(res + "\t" + System.currentTimeMillis());
                });
            }
            
            
        }
        
        executorService.shutdown();
    }
    
}
