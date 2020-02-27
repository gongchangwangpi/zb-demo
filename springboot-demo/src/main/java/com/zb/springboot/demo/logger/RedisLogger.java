package com.zb.springboot.demo.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * @author zhangbo
 * @date 2019-12-10
 */
public class RedisLogger extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent event) {
        String msg = getMsg(event);
        System.out.println(msg);
    }

    private String getMsg(ILoggingEvent event) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(" ")
                .append(event.getLevel())
                .append(" [")
                .append(event.getThreadName())
                .append("] ")
                .append(event.getLoggerName())
                .append(" : ")
                .append(event.getFormattedMessage());


        return stringBuilder.toString();
    }
}
