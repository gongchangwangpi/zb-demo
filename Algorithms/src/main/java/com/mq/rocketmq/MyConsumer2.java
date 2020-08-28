package com.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by books on 2017/11/13.
 */
public class MyConsumer2 {

    private final Logger logger = LoggerFactory.getLogger(MyConsumer2.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private DefaultMQPullConsumer defaultMQPullConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    
    private int count = 0;

    /**
     * Spring bean init-method
     */
    public void init() throws InterruptedException, MQClientException {

        // 参数信息
        logger.info("DefaultMQPushConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
//        defaultMQPullConsumer = new DefaultMQPullConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

//        defaultMQPushConsumer.setConsumeThreadMin(1);
//        defaultMQPushConsumer.setConsumeThreadMax(1);
        
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe("MyTopic2", "MyTag2");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
//        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);

        // 注册消息监听 MessageListenerOrderly / MessageListenerConcurrently
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(java.util.List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            
                logger.info(JSON.toJSONString(msgs));
                
                MessageExt msg = msgs.get(0);
//                if (msg.getTopic().equals("MyTopic2")) {
                    // TODO 执行Topic的消费逻辑
//                    if (msg.getTags() != null && msg.getTags().equals("MyTag2")) {
                        // TODO 执行Tag的消费
                        logger.info(" ----->>>>> " + new String(msg.getBody()) + " " + count++);
//                    }
//                }
                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();

        logger.info("DefaultMQPushConsumer start success!");
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    // ----------------- setter --------------------

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }
    
}
