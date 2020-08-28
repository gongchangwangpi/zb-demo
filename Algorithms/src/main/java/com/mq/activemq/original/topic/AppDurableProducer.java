package com.mq.activemq.original.topic;

import com.mq.activemq.original.Constants;
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

    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);

        Connection connection = connectionFactory.createConnection();
        connection.setClientID("test-order0");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(Constants.DURABLE_TOPIC_NAME);

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
