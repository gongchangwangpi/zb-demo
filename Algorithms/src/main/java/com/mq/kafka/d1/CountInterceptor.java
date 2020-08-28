package com.mq.kafka.d1;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020-02-25
 */
public class CountInterceptor implements ProducerInterceptor<String, String> {

    private static AtomicInteger total = new AtomicInteger();
    private static AtomicInteger success = new AtomicInteger();
    private static AtomicInteger fail = new AtomicInteger();

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return new ProducerRecord<>(record.topic(), record.key(), "-" + total.incrementAndGet() + "-" + record.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            success.incrementAndGet();
        } else {
            fail.incrementAndGet();
        }
    }

    @Override
    public void close() {
        System.out.println("total send : " + total.get());
        System.out.println("success send : " + success.get());
        System.out.println("fail send : " + fail.get());
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
