package com.activemq.original.p2p;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 点对点模式下，针对同一个队列的多个消费者，消息几乎是平均的分发的每个消费者
 * 如队列中有50条消息，同时有2个消费者，则每个消费者消息25条消息
 *
 *
 * Created by Administrator on 2018/2/26 0026.
 */
public class AppConsumer {

    // 集群abc三台服务器，bc为master/salve，ab,ac为同步，a仅作为消费者，不对外提供服务
    // bc中为master的节点对外提供服务，当master节点挂掉之后，剩下的salve节点充当master节点，对外提供服务
    // 集群配置，消费者配置集群中的全部节点
    private static final String URL = "failover:(tcp://192.168.0.103:61616,tcp://192.168.0.103:61617,tcp://192.168.0.103:61618)?randomize=true";
//    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue-test";

    public static void main(String[] args) throws Exception {
        // 1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        Connection connection = connectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener((message) -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
        });

        /*
        while (true) {
            Message message = consumer.receive(100 * 1000);
            System.out.println("接收消息： " + message);
        }
        */

        // 消息监听是异步的，所以不要在main里面立即关闭
//        connection.close();
    }

}
