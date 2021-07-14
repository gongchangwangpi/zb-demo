package com.mq.rocketmq;

import com.mq.rocketmq.config.AppConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

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
            // 设置延时消息等级，默认有几个等级，也可通过配置修改默认等级
//            message.setDelayTimeLevel(3);
            // 设置自定义properties，消费者方可使用该属性过滤该消息
            // defaultMQPushConsumer.subscribe("MyTopic1", MessageSelector.bySql("a between 0 and 5"));
//            message.putUserProperty("", "");
            SendResult sendResult = null;
            try {
                // 同步发送
                sendResult = producer.getDefaultMQProducer().send(message);
                // 异步发送
//                producer.getDefaultMQProducer().send(message, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                    }
//                    @Override
//                    public void onException(Throwable e) {
//                    }
//                });

                // 单向发送
//                producer.getDefaultMQProducer().sendOneway(message);

                // 消息有序性
//                producer.getDefaultMQProducer().send(message, new MessageQueueSelector() {
//                    @Override
//                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                        // arg为send方法传入的第三个参数
//                        return null;
//                    }
//                }, i);

                // 事务消息
                producer.getDefaultMQProducer().sendMessageInTransaction(message, null, null);
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
