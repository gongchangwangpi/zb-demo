package com.mq.rabbitmq.delay;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangbo
 * @date 2020/12/11
 */
@Slf4j
public class RabbitDelayQueueTest {

    private static final String DELAY_EXCHANGE = "delay-exchange";
    private static final String DLX_EXCHANGE = "dlx-exchange";
    private static final String DELAY_QUEUE = "delay-queue";
    private static final String DLX_QUEUE = "dlx-queue";
    private static final String ROUTING_KEY = "delay";
    private static final String DLX_ROUTING_KEY = "dlx-routing";

    private static final int MESSAGE_TTL = 10 * 1000;

    public static void main(String[] args) throws Exception {

        // 获取channel
        Channel channel = getChannel();

        // 声明死信交换器和队列
        declareDlxExchangeAndQueue(channel);

        // 声明普通交换器和队列(带过期时间)，并绑定到私信交换器
        declareCommonQueueBindDlx(channel);

        // 添加消费者消费死信队列
        consumeDlxQueue(channel);

        // 发布消息
        publishMsg(channel);

    }

    private static void publishMsg(Channel channel) throws IOException {
        for (int i = 0; i < 5; i++) {
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .expiration(String.valueOf(1000 * 3 * i))
                    .build();

            String msg = "delay-message: " + i;
            log.info("publish msg: {}", msg);
            channel.basicPublish(DELAY_EXCHANGE, ROUTING_KEY, properties, msg.getBytes());
        }
    }

    private static void consumeDlxQueue(Channel channel) throws IOException {
        channel.basicConsume(DLX_QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consumerTag: {}, envelope = {}, properties = {}, body = {}", consumerTag, envelope, properties, new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

    private static void declareCommonQueueBindDlx(Channel channel) throws IOException {
        // 声明exchange
        channel.exchangeDeclare(DELAY_EXCHANGE, BuiltinExchangeType.TOPIC, true);
        // 声明队列
        Map<String, Object> queueMap = new HashMap<>();
        queueMap.put("x-message-ttl", MESSAGE_TTL);
        // 死信交换器
        queueMap.put("x-dead-letter-exchange", DLX_EXCHANGE);
        // 设置死信交换器的routing key，没有设置则使用原队列的routing key
        queueMap.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        channel.queueDeclare(DELAY_QUEUE, true, false, false, queueMap);
        // 队列绑定到交换器
        channel.queueBind(DELAY_QUEUE, DELAY_EXCHANGE, ROUTING_KEY);
    }

    private static void declareDlxExchangeAndQueue(Channel channel) throws IOException {
        // 私信交换器和队列，就是一个普通的交换器和队列
        channel.exchangeDeclare(DLX_EXCHANGE, BuiltinExchangeType.TOPIC, true);
        Map<String, Object> dlxQueueMap = new HashMap<>();
        channel.queueDeclare(DLX_QUEUE, true, false, false, dlxQueueMap);
        channel.queueBind(DLX_QUEUE, DLX_EXCHANGE, DLX_ROUTING_KEY);
    }

    private static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUsername("");
//        factory.setPassword("");
//        factory.setHost("");
//        factory.setPort(5672);
//        factory.setUri("amqp://username:password@ip:port/virtualHost");

        // 建立一个连接
        Connection connection = factory.newConnection();

        // 建立信道
        return connection.createChannel();
    }

}
