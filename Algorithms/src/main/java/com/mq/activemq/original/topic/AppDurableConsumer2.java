package com.mq.activemq.original.topic;

import com.mq.activemq.original.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久主题，需要先运行一次Consumer，到消息队列服务器去注册持久主题
 * 后面如果消费者挂掉，也可以消费挂掉之后的主题
 * 
 * 非持久化主题，只能消费消费者订阅后的消息，之前的消息不能消费
 *
 * Created by Administrator on 2018/2/26 0026.
 */
public class AppDurableConsumer2 {

    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);

        Connection connection = connectionFactory.createConnection();
        // 持久topic
        connection.setClientID("test-order2");
        
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(Constants.DURABLE_TOPIC_NAME);

//        MessageConsumer consumer = session.createConsumer(topic);

        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "test-order2");

        subscriber.setMessageListener((message) -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
        });

        // 消息监听是异步的，所以不要在main里面立即关闭
//        connection.close();
    }

}
