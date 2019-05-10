package com.activemq.original.p2p;

import com.activemq.original.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2018/2/26 0026.
 */
public class AppTransactionProducer {
    
    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.URL);
        
        Connection connection = null;
        
        try {
            
            connection = connectionFactory.createConnection();
            connection.start();
            // 事务消息,保证一批次的消息要么都发送成功，要么都发送失败，取决于后面的session.commit();
            // 
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(Constants.QUEUE_NAME);

            MessageProducer producer = session.createProducer(queue);

            for (int i = 0; i < 1; i++) {
                TextMessage message = session.createTextMessage("test" + i);
                producer.send(message);
                System.out.println("发送成功：" + message.getText());
            }

            // 
            session.commit();
//            session.rollback();
            session.close();
        } finally {
            connection.close();
        }

    }

}
