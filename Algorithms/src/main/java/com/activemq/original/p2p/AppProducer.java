package com.activemq.original.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2018/2/26 0026.
 */
public class AppProducer {

    // 集群abc三台服务器，bc为master/salve，ab,ac为同步，a仅作为消费者，不对外提供服务
    // bc中为master的节点对外提供服务，当master节点挂掉之后，剩下的salve节点充当master节点，对外提供服务
    // 集群配置，生产者仅需配置master/salve节点
    private static final String URL = "failover:(tcp://192.168.0.103:61617,tcp://192.168.0.103:61618)?randomize=true";
//    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue-test";

    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);

        for (int i = 0; i < 50; i++) {
            TextMessage message = session.createTextMessage("test" + i);
            producer.send(message);
            System.out.println("发送成功：" + message.getText());
        }

        connection.close();
    }

}
