package com.middlesoftware.netty.im.util;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangbo
 */
public class ChannelHolder {

    private ChannelHolder() {
    }

    public static final ConcurrentHashMap<String, Channel> CHANNEL_HOLDER = new ConcurrentHashMap<>(16);
    
//    public static void put()
    
}
