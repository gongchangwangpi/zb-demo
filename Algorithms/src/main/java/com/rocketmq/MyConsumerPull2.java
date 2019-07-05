package com.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by books on 2017/11/13.
 */
public class MyConsumerPull2 {

    private final Logger logger = LoggerFactory.getLogger(MyConsumerPull2.class);

    private DefaultMQPullConsumer defaultMQPullConsumer;
    private String namesrvAddr;
    private String consumerGroup;

    private int count = 0;
    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    /**
     * Spring bean init-method
     */
    public void init() throws InterruptedException, MQClientException {

        // 参数信息
        logger.info("defaultMQPullConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPullConsumer = new DefaultMQPullConsumer(consumerGroup);
        defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPullConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPullConsumer.start();

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费

        // 设置为集群消费(区别于广播消费)
        defaultMQPullConsumer.setMessageModel(MessageModel.CLUSTERING);
//        defaultMQPullConsumer.setMessageModel(MessageModel.BROADCASTING);

        // 注册消息监听 MessageListenerOrderly / MessageListenerConcurrently
        Set<MessageQueue> messageQueues = defaultMQPullConsumer.fetchSubscribeMessageQueues("MyTopic2");
        messageQueues.forEach(queue -> {
            PullResult pullResult = null;
            try {
                pullResult = defaultMQPullConsumer.pull(queue, "MyTag2", getOffset(queue), 32);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setOffset(queue, pullResult.getNextBeginOffset());
            if (pullResult.getPullStatus() == PullStatus.FOUND) {
                List<MessageExt> msgList = pullResult.getMsgFoundList();
                logger.info(" ====== pull messages: count: {}", msgList.size());
                msgList.forEach(msg -> {
                    logger.info(" ====== pull message: {}", new String(msg.getBody()) + " " + count++);
                });
            }
        });

        logger.info("defaultMQPullConsumer start success!");
    }

    private void setOffset(MessageQueue queue, long offset) {
        offsetTable.put(queue, offset);
    }

    private long getOffset(MessageQueue queue) {
        return offsetTable.getOrDefault(queue, 0L);
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPullConsumer.shutdown();
    }

    // ----------------- setter --------------------

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }
    
}
