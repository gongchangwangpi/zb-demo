package com.mq.rabbitmq.origin;

import com.mq.rabbitmq.ProviderUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * @author zhangbo
 */
public class Provider {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_test";
    private static String routingkeyName = "routingkey_test";

    public static void main(String[] args) throws Exception {

        Channel channel = ProviderUtil.getChannel(exchangeName, queueName, routingkeyName, ProviderUtil.EXCHANGE_DIRECT);

        String message = "hello rabbitmq";
        
        channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("publish message success");
        // 模拟异常,在没有事务或者发送者确认机制下,发送者是不知道具体发送成功的
        int i = 1 / 0;
        
        ProviderUtil.close();
    }
    
}
