package com.netty.im.v2.handler;

import com.alibaba.fastjson.JSON;
import com.netty.im.packet.ChatPacket;
import com.netty.im.util.ChannelHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<ChatPacket> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatPacket msg) throws Exception {
        
        log.info("chat ----- {}", JSON.toJSONString(msg));
        
        String toUid = msg.getToUid();
        Channel channel = ChannelHolder.CHANNEL_HOLDER.get(toUid);
        
        if (channel != null) {
            channel.writeAndFlush(msg);
        } else {
            msg.setMessage(toUid + "不存在或不在线,请稍后再试");
            ctx.writeAndFlush(msg);
        }
    }
}
