package com.activemq.original.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Created by Administrator on 2018/2/26 0026.
 */
public class AppDurableProducer {

    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "topic-test";

    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        Connection connection = connectionFactory.createConnection();
        connection.setClientID("test-order");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);

        for (int i = 0; i < 50; i++) {
            TextMessage message = session.createTextMessage("test" + i);
            producer.send(message);
            System.out.println("发送成功：" + message.getText());
        }

        session.close();
        connection.close();
    }

}
