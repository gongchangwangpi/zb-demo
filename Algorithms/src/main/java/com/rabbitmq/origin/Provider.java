package com.rabbitmq.origin;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangbo
 */
public class Provider {
    
    private static String exchangeName = "exchange_test";
    private static String queueName = "queue_test";
    private static String routingkeyName = "routingkey_test";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        AMQP.Exchange.DeclareOk exchangeDeclare = channel.exchangeDeclare(exchangeName, "direct", true, false, null);

        System.out.println("declare exchange: " + exchangeDeclare);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queueName, true, false, false, null);
        System.out.println("queue exchange: " + queueDeclare);
        
        channel.queueBind(queueName, exchangeName, routingkeyName);
        
        String message = "hello rabbitmq";
        
        channel.basicPublish(exchangeName, routingkeyName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("publish message success");
        
        channel.close();
        connection.close();
    }
    
}
