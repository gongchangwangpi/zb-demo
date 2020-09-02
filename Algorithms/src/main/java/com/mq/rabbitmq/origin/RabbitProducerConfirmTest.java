package com.mq.rabbitmq.origin;

import com.mq.rabbitmq.RabbitProducerUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 发送方确认机制
 * 
 * @author zhangbo
 */
@Slf4j
public class RabbitProducerConfirmTest {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_confirm_test";
    private static String routingkeyName = "routingkey_test";
    
    private static Map<Long, String> unconfirmedMessages = new TreeMap<>();
    

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitProducerUtil.getChannel(exchangeName, queueName, routingkeyName, RabbitProducerUtil.EXCHANGE_DIRECT);

        // 开启确认机制,和事务互斥,在queue上开启了事务就不能再开启确认,反之亦然.
        channel.confirmSelect();

        /*
        channel.addConfirmListener(new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                // 消息发送成功
                if (multiple) {
                    unconfirmedMessages.entrySet().removeIf((entry) -> entry.getKey() <= deliveryTag);
                } else {
                    unconfirmedMessages.remove(deliveryTag);
                }
                log.info("【消息发送确认】deliveryTag = {}, multiple = {}, unconfirmedMessages = {}", deliveryTag, multiple, unconfirmedMessages);
            }
        }, new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                // 消息发送失败,可以重发消息
                if (multiple) {
                    unconfirmedMessages.entrySet().removeIf((entry) -> entry.getKey() <= deliveryTag);
                } else {
                    String message = unconfirmedMessages.remove(deliveryTag);
                    channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                }
                log.info("【消息发送确认】deliveryTag = {}, multiple = {}, unconfirmedMessages = {}", deliveryTag, multiple, unconfirmedMessages);
            }
        });
        */
        
        // 添加异步确认回调
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                // 消息发送成功
                if (multiple) {
                    unconfirmedMessages.entrySet().removeIf((entry) -> entry.getKey() <= deliveryTag);
                } else {
                    unconfirmedMessages.remove(deliveryTag);
                }
                log.info("【消息发送确认】deliveryTag = {}, multiple = {}, unconfirmedMessages = {}", deliveryTag, multiple, unconfirmedMessages);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                // 消息发送失败,可以重发消息
                if (multiple) {
                    unconfirmedMessages.entrySet().removeIf((entry) -> entry.getKey() <= deliveryTag);
                } else {
                    String message = unconfirmedMessages.remove(deliveryTag);
                    channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                }
                log.info("【消息发送确认】deliveryTag = {}, multiple = {}, unconfirmedMessages = {}", deliveryTag, multiple, unconfirmedMessages);
            }
        });

        for (int i = 1; i <= 20; i++) {
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            // 一次性发送20条消息
            String message = "hello rabbitmq" + i;
            channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 将未确认的消息和编码放入缓存,在确认监听器中使用
            unconfirmedMessages.put(nextPublishSeqNo, message);
        }
        System.out.println("publish message success");
        
        /*
        // 同步确认的话,不要每条消息都同步确认,性能不好
        if (!channel.waitForConfirms()) {
            // 同步等待rabbit服务器的确认,推荐使用异步监听器
        }
        */

        // 等待5秒,不然channel关闭后,不能收到确认消息
        TimeUnit.SECONDS.sleep(5);
        RabbitProducerUtil.close();
    }
    
}
