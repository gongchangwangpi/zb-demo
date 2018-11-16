package com.netty.im.v2.handler;

import com.alibaba.fastjson.JSON;
import com.netty.im.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("---------------------------");
        log.info(JSON.toJSONString(msg));
        log.info("---------------------------");
    }
}
