package com.middlesoftware.dubbo.consumer;

import com.books.bingfayishu.d4.SleepUtils;
import com.middlesoftware.dubbo.api.HelloService;

/**
 * @author zhangbo
 */
public class ConsumerApplication {

    public static void main(String[] args) {

        HelloService helloService = ConsumerConfiguration.init();

        System.out.println("------------------" + helloService.getClass());
        
        String test = helloService.hello("test");

        System.out.println(test);

        for (int i = 0; i < 10; i++) {
            // 休眠2秒后关闭服务提供者，模拟失败
            SleepUtils.second(2);

            String test2 = helloService.hello("test" + i);
            System.out.println(test2);
        }

    }
    
}
