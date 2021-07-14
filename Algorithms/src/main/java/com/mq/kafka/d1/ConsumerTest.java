package com.mq.kafka.d1;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.compress.utils.Lists;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;


/**
 * @author zhangbo
 * @date 2020-02-20
 */
public class ConsumerTest {

    public static void main(String[] args) {

        Properties props = new Properties();
//        props.setProperty("bootstrap.servers", "localhost:9093,localhost:9092,localhost:9091");
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.206.6.100:9092");
//        props.setProperty("zookeeper", "localhost:2181");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 延迟提交offset
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅主题
        String resubscribe_topic = "resubscribe_topic";
        consumer.subscribe(Arrays.asList("trace_collect", resubscribe_topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            List<String> resubTopicList = Lists.newArrayList();

            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                if (resubscribe_topic.equals(topic)) {
                    // 收集需要重新订阅的topic
                    resubTopicList.add(record.value());
                } else {
                    // 正常消费其他topic
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n", record.partition(), record.offset(), record.key(), record.value());
                }
            }
            // 根据新的topic重新订阅
            if (CollUtil.isNotEmpty(resubTopicList)) {
                consumer.subscribe(resubTopicList);
            }
            // 需设置自动提交offset为false
            // 同步阻塞提交
//            consumer.commitSync();
//            // 异步提交
//            consumer.commitAsync((metadataMap, exception) -> {
//
//            });
        }

    }

}
