package com.mq.rocketmq;

import com.mq.rocketmq.config.AppConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * RocketMQ启动类
 * 
 * Created by books on 2017/11/13.
 */
public class Application {
    
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws MQClientException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MyProducer producer = context.getBean(MyProducer.class);

//        producer.getDefaultMQProducer().createTopic("topic_key", "MyTopic1", 4);
//        producer.getDefaultMQProducer().createTopic("topic_key", "MyTopic2", 4);

        for (int i = 0; i < 100; i++) {
            String msg = "hello rocketmq 你好 " + i;
            int j = ((i % 2) + 1);
            Message message = new Message("MyTopic" + j, "MyTag" + j, msg.getBytes());
            SendResult sendResult = null;
            try {
                sendResult = producer.getDefaultMQProducer().send(message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            // 当消息发送失败时如何处理
            if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
                // TODO
                System.out.println("---->>>>  发送失败");
            }
        }

        logger.info("============ 消息发送成功 ============");

    }
    
}
