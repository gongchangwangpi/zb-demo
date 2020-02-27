package com.kafka.d1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author zhangbo
 * @date 2020-02-20
 */
public class ConsumerTest {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9093,localhost:9092,localhost:9091");
//        props.setProperty("zookeeper", "localhost:2181");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        // 延迟提交offset
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅主题
        consumer.subscribe(Arrays.asList("first", "cluster-partition-3"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n", record.partition(), record.offset(), record.key(), record.value());
            }
        }

    }

}
