package com.middlesoftware.netty.im.v2.handler;

import com.middlesoftware.netty.im.packet.LoginResponsePacket;
import com.util.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author zhangbo
 */
@Slf4j
public class LoginClientHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        System.err.println(DateUtil.defaultFormatDateTime(new Date()) + "\t系统消息：\n" + msg.getMessage());
    }
}
