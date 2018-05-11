package com.netty.inaction.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by books on 2017/11/17.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(EchoServerHandler.class);
    

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" Server read msg : " + msg);
        ctx.write(" server return : " + msg);
    }
    
}
