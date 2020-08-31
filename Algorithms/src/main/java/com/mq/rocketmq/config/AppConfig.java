package com.mq.rocketmq.config;

import com.mq.rocketmq.MyConsumerPull1;
import com.mq.rocketmq.MyConsumerPull2;
import com.mq.rocketmq.MyProducer;
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
        producer.setNamesrvAddr(RocketConstant.NAMESRV_ADDR);
        producer.setProducerGroup("MyProducerGroup");
        return producer;
    }
    
    /*@Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumer1 consumer1() throws MQClientException, InterruptedException {
        MyConsumer1 consumer = new MyConsumer1();
        consumer.setNamesrvAddr(RocketConstant.NAMESRV_ADDR);
        consumer.setConsumerGroup("MyConsumerGroup1");
        return consumer;
    }
    
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumer2 consumer2() throws MQClientException, InterruptedException {
        MyConsumer2 consumer = new MyConsumer2();
        consumer.setNamesrvAddr(RocketConstant.NAMESRV_ADDR);
        consumer.setConsumerGroup("MyConsumerGroup2");
        return consumer;
    }*/

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumerPull1 pullConsumer1() throws MQClientException, InterruptedException {
        MyConsumerPull1 consumer = new MyConsumerPull1();
        consumer.setNamesrvAddr(RocketConstant.NAMESRV_ADDR);
        consumer.setConsumerGroup("MyConsumerGroup2");
        return consumer;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MyConsumerPull2 pullConsumer2() throws MQClientException, InterruptedException {
        MyConsumerPull2 consumer = new MyConsumerPull2();
        consumer.setNamesrvAddr(RocketConstant.NAMESRV_ADDR);
        consumer.setConsumerGroup("MyConsumerGroup2");
        return consumer;
    }
    
}

