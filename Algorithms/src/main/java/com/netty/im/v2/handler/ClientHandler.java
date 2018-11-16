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
public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("----- channelActive");
        new Thread(new ConsoleHandler(ctx)).start();
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("---------------------------");
        log.info(JSON.toJSONString(msg));
        log.info("---------------------------");
    }
}
