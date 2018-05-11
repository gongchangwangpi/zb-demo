package com.rocketmq.config;

import com.rocketmq.MyConsumer1;
import com.rocketmq.MyProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 * 
 * Created by books on 2017/11/13.
 */
@Configuration
public class AppConfig {
    
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyProducer producer() throws MQClientException {
        MyProducer producer = new MyProducer();
        producer.setNamesrvAddr("172.18.8.21:9876");
        producer.setProducerGroup("MyProducerGroup");
        return producer;
    }
    
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumer1 consumer1() throws MQClientException, InterruptedException {
        MyConsumer1 consumer = new MyConsumer1();
        consumer.setNamesrvAddr("172.18.8.21:9876");
        consumer.setConsumerGroup("MyConsumerGroup1");
        return consumer;
    }
    
    /*@Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumer2 consumer2() throws MQClientException, InterruptedException {
        MyConsumer2 consumer = new MyConsumer2();
        consumer.setNamesrvAddr("172.18.8.21:9876");
        consumer.setConsumerGroup("MyConsumerGroup2");
        return consumer;
    }*/
    
}

