package com.mq.kafka.d1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;

/**
 * 示例见{@link KafkaProducer}最上面的注释
 *
 * @author zhangbo
 * @date 2020-02-19
 */
public class ProducerTest {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "10.206.6.100:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ArrayList<Class> interceptors = new ArrayList<>();
        // 添加的顺序即为拦截器执行的顺序
        interceptors.add(TimestampInterceptor.class);
        interceptors.add(CountInterceptor.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        Producer<String, String> producer = new KafkaProducer<>(props);
//        for (int i = 0; i < 10; i++) {
//            producer.send(new ProducerRecord<>("cluster-partition-3", Integer.toString(i), Integer.toString(i)));
//        }
        producer.send(new ProducerRecord<>("trace_collect", Integer.toString(0), Integer.toString(0)));

        producer.close();

    }

}
