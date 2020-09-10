package com.mq.rabbitmq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
@Slf4j
public class RabbitProducerUtil {
    
    private static final String DEFAULT_EXCHANGENAME = "exchange_test";
    private static final String DEFAULT_QUEUENAME = "queue_test";
    private static final String DEFAULT_ROUTINGKEYNAME = "routingkey_test";

    /**
     * @see BuiltinExchangeType
     */
    public static final String EXCHANGE_DIRECT = "direct";

    public static final String EXCHANGE_TOPIC = "topic";
    
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Channel> channelThreadLocal = new ThreadLocal<>();

    private RabbitProducerUtil() {
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

        // 1.建立一个连接
        Connection connection = factory.newConnection();
        connectionThreadLocal.set(connection);
        // 2.通过连接建立一个通道
        Channel channel = connection.createChannel();
        channelThreadLocal.set(channel);

        Map<String, Object> exchangeArgs = getExchangeArgs();
        Map<String, Object> queueArgs = getQueueArgs();

        // AMQP 封装了AMQP中的各个命令
        // 3.声明交换器类型和名称
        AMQP.Exchange.DeclareOk exchangeDeclare = channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, exchangeArgs);
        // 4.声明队列名称等
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queueName, true, false, false, queueArgs);
        // 5.队列和交换器通过routing key绑定
        channel.queueBind(queueName, exchangeName, routingkeyName);
        // 6.使用通道发送消息
        return channel;
    }

    private static Map<String, Object> getQueueArgs() {
        HashMap<String, Object> args = new HashMap<>();
        // 死信交换器,绑定在队列上。当出现以下3种情况时，消息将被路由到死信交换器
        // 1.将队列上的消息对被消费者拒绝(Basic.Reject/Basic.Nack)且requeue为false时
        // 2.消息过期(声明队列时加入x-message-ttl参数，单位毫秒；或x-expires参数声明队列的过期时间，单位毫秒；或者在发送消息时，加入消息的expiration参数，单位毫秒)
        // 3.队列达到最大长度(声明队列时加x-max-length参数)
//        args.put("x-dead-letter-exchange", "dlx-exchange-name");
        // 设置死信交换器的routing key，没有设置则使用原队列的routing key
//        args.put("x-dead-letter-routing-key", "dlx-routing-key");
        return args;
    }

    private static Map<String, Object> getExchangeArgs() {
        HashMap<String, Object> args = new HashMap<>();
        // 备份交换器，当生产者发送的消息不能被正确的路由到队列时，消息将被路由到该备份交换器
        // 设置备份交换器后，队列参数mandatory无效。
        // 如果备份交换器同样不能正确路由消息时，消息将丢失，且生产者没有任何异常
//        args.put("alternate-exchange", "alternate-exchange-name");
        return args;
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
