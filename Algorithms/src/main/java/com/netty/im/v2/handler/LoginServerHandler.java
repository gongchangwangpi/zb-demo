package com.netty.im.v2.handler;

import com.alibaba.fastjson.JSON;
import com.netty.im.packet.LoginRequestPacket;
import com.netty.im.packet.LoginResponsePacket;
import com.netty.im.util.Attributes;
import com.netty.im.util.ChannelHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class LoginServerHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        
        log.info("login ----- {}", JSON.toJSONString(msg));
        // 设置channel的用户信息
        Channel channel = ctx.channel();
        Attribute<LoginRequestPacket> attribute = channel.attr(Attributes.USER);
        attribute.set(msg);
        
        ChannelHolder.CHANNEL_HOLDER.putIfAbsent(msg.getUid(), channel);
        
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUid(msg.getUid());
        loginResponsePacket.setUsername(msg.getUsername());
        loginResponsePacket.setMessage("登陆成功");
        
        channel.writeAndFlush(loginResponsePacket);
    }
}
