package com.mq.rabbitmq.origin;

import com.mq.rabbitmq.RabbitProducerUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
@Slf4j
public class RabbitProducerTest {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_test";
//    private static String routingkeyName = "routingkey_test";
    private static String routingkeyName = "routingkey_test";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitProducerUtil.getChannel(exchangeName, queueName, routingkeyName, RabbitProducerUtil.EXCHANGE_DIRECT);

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.warn("return msg, replyCode: {}, replyText: {}, exchange: {}, routingKey: {}, body: {}", replyCode, replyText, exchange, routingKey, new String(body));
            }
        });

        String message = "hello rabbitmq";

        for (int i = 0; i < 5; i++) {
            message += i;
            // mandatory强制的，如果message没有被exchange正确的route到queue，则rabbitmq服务器会返回该消息
            channel.basicPublish(exchangeName, "123routingkeyName_mandatory", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
        }
        System.out.println("publish message success");
        // 模拟异常,在没有事务或者发送者确认机制下,发送者是不知道具体发送成功的
//        int i = 1 / 0;

        TimeUnit.SECONDS.sleep(5);

        RabbitProducerUtil.close();
    }
    
}
