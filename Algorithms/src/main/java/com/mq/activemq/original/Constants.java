package com.mq.activemq.original;

/**
 * @author zhangbo
 */
public class Constants {

    // 集群abc三台服务器，bc为master/salve，ab,ac为同步，a仅作为消费者，不对外提供服务
    // bc中为master的节点对外提供服务，当master节点挂掉之后，剩下的salve节点充当master节点，对外提供服务
    // 集群配置，生产者仅需配置master/salve节点
//    public static final String URL = "failover:(tcp://192.168.0.103:61617,tcp://192.168.0.103:61618)?randomize=true";
    public static final String URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue-test";
    public static final String TOPIC_NAME = "topic-test";
    public static final String DURABLE_TOPIC_NAME = "durable-topic-test";

}
