package com.kafka.d1;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @author zhangbo
 * @date 2020-02-27
 */
@Slf4j
public class TimestampProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        log.info(" ---- init ");
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String s = new String(value);
        String newValue = s + "-" + System.currentTimeMillis();
        log.info(" ---- process, key = {}, origin value = {}, new value = {}", key, s, newValue);
        context.forward(key, newValue.getBytes());
    }

    @Override
    public void close() {

    }
}
