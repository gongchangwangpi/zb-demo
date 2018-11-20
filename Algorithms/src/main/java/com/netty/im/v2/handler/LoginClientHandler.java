package com.netty.im.v2.handler;

import com.netty.im.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class LoginClientHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        System.err.println(msg.getMessage());
    }
}
