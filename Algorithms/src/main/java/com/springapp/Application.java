package com.springapp;

import com.springapp.redis_pubsub.RedisPubSubConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring application 启动类
 *
 * Created by books on 2017/9/4.
 */
public class Application {
    
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-app.xml"}, true);


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedisPubSubConfig.class);
        context.start();
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);

        logger.info("Sending message...");
//        template.convertAndSend("redisChatTest", "Hello world from Redis!");
        
//        System.in.read();
    }

    @Transactional
    public void methodA() {
        // insert 插入数据
        methodB();
        int i = 1 / 0; // 模拟抛异常
    }
    @Transactional
    public void methodB() {
        // insert 插入数据
    }

}
