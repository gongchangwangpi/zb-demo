package com.netty.inaction.example;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by books on 2017/11/17.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(EchoServerHandler.class);
    
    private static final AtomicInteger count = new AtomicInteger();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (msg instanceof io.netty.buffer.ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            byteBuf.readBytes(System.out, byteBuf.capacity());
        }
        
//        System.out.println(" Server read msg : " + msg);
        ctx.write(" server return : " + count.incrementAndGet());
    }
    
}
