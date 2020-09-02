package com.mq.rabbitmq.origin;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 生产者和消费者共用同一个channel测试
 *
 * @author zhangbo
 * @date 2020/8/31
 */
@Slf4j
public class ProducerConsumerTest {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        String exchange = "exchange_common";
        String queue = "queue_common";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDelete(exchange);

        channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT, true, false, null);

        channel.queueDeclare(queue, true, false, false, null);

        channel.queueBind(queue, exchange, queue);

        for (int i = 0; i < 5; i++) {
            String msg = "hello " + i;
            channel.basicPublish(exchange, queue, null, msg.getBytes());
            log.info("publish msg: {}", msg);
        }

        TimeUnit.SECONDS.sleep(1);
        log.info("======");

        channel.basicQos(64);

        channel.basicConsume(queue, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("receive msg: {}", new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        TimeUnit.SECONDS.sleep(2);

        log.info("shutdown...");
        channel.close();
        connection.close();

    }

}
