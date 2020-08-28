package com.mq.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangbo
 */
@Slf4j
public class ProviderUtil {
    
    private static final String DEFAULT_EXCHANGENAME = "exchange_test";
    private static final String DEFAULT_QUEUENAME = "queue_test";
    private static final String DEFAULT_ROUTINGKEYNAME = "routingkey_test";
    
    public static final String EXCHANGE_DIRECT = "direct";
    public static final String EXCHANGE_TOPIC = "topic";
    
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Channel> channelThreadLocal = new ThreadLocal<>();

    private ProviderUtil() {
    }
    
    public static Channel getChannel(String exchangeName, String queueName, String routingkeyName, String exchangeType) throws Exception {
        if (StringUtils.isEmpty(exchangeType)) {
            throw new IllegalArgumentException("exchangeType is empty");
        }
        
        exchangeName = StringUtils.defaultIfEmpty(exchangeName, DEFAULT_EXCHANGENAME);
        queueName = StringUtils.defaultIfEmpty(queueName, DEFAULT_QUEUENAME);
        routingkeyName = StringUtils.defaultIfEmpty(routingkeyName, DEFAULT_ROUTINGKEYNAME);
        
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUsername("");
//        factory.setPassword("");
//        factory.setHost("");
//        factory.setPort(5672);

//        factory.setUri("amqp://username:password@ip:port/virtualHost");

        Connection connection = factory.newConnection();
        connectionThreadLocal.set(connection);

        Channel channel = connection.createChannel();
        channelThreadLocal.set(channel);
        AMQP.Exchange.DeclareOk exchangeDeclare = channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, exchangeName, routingkeyName);
        
        return channel;
    }
    
    public static void close() {
        closeQuietly(channelThreadLocal.get());
        closeQuietly(connectionThreadLocal.get());
    }
    
    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.error("【rabbitmq-provider】关闭失败", e);
            }
        }
    }
}
