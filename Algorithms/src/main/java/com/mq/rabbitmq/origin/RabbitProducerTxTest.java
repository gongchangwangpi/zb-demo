package com.mq.rabbitmq.origin;

import com.mq.rabbitmq.RabbitProducerUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 事务消息
 * 
 * @author zhangbo
 */
@Slf4j
public class RabbitProducerTxTest {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_test";
    private static String routingkeyName = "routingkey_test";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitProducerUtil.getChannel(exchangeName, queueName, routingkeyName, RabbitProducerUtil.EXCHANGE_DIRECT);

        String message = "hello rabbitmq";
        
        channel.txSelect();

        try {
            channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("publish message success");
            // 模拟异常,消息不会被消费
//            int i = 1 / 0;

            // 模拟耗时,消费者会在5秒后,等待事务提交后才能消费消息
//            TimeUnit.SECONDS.sleep(5);
            
            channel.txCommit();
        } catch (Exception e) {
            log.error("【事务消息】发送失败", e);
            channel.txRollback();
        }

        RabbitProducerUtil.close();
    }
    
}
