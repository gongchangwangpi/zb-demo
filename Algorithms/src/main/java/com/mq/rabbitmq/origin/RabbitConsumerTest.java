package com.mq.rabbitmq.origin;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.ConsumerWorkService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 */
@Slf4j
public class RabbitConsumerTest {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_test";
    private static String routingkeyName = "routingkey_test";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        // 最多接收未被ack的消息个数
        channel.basicQos(64);

        Random random = new Random();
        AtomicInteger count = new AtomicInteger(0);

        /**
         * 默认是多线程消费
         * @see ConsumerWorkService#ConsumerWorkService(java.util.concurrent.ExecutorService, java.util.concurrent.ThreadFactory, int, int)
         */
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            // 同一个消费者消费多个队列时，使用consumerTag区分
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    log.warn("InterruptedException ==>", e);
                }

//                int i = random.nextInt(5);
//                log.info("receive message: {}, delivery tag = {}, random value = {}, ack = {}", new String(body), envelope.getDeliveryTag(), i, i % 2 == 0);
//                if (i % 2 == 0) {
                // 单条消息确认
//                    channel.basicAck(envelope.getDeliveryTag(), false);
//                } else {
                // 消息重发
//                    channel.basicRecover();
//                }

                //
                log.info("receive message: {}, delivery tag = {}, count = {}, ack = {}", new String(body), envelope.getDeliveryTag(), count.get(), count.get() % 3 == 0);
                if (count.getAndIncrement() % 3 == 0) {
                    // 单条消息ack
//                    channel.basicAck(envelope.getDeliveryTag(), false);
                    // 批量确认，比deliveryTag小的全部ack
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            }
        };

        channel.basicConsume(queueName, false, "consumer3", consumer);
        // 同一个消费者消费多个队列时，使用consumerTag区分
//        channel.basicConsume("some_queue", false, "consumer2", consumer);

        channel.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                if (cause.isHardError()) {
                    Connection conn = (Connection) cause.getReference();
                } else {
                    Channel chan = (Channel) cause.getReference();
                }
                if (cause.isInitiatedByApplication()) {
                    // 应用自己关闭
                }
                Method reason = cause.getReason();
            }
        });

        // 异步消费，不能立即关闭
//        channel.close();
//        connection.close();
    }
    
}
