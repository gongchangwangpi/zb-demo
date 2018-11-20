package com.netty.im.v2.handler;

import com.netty.im.packet.ChatPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zhangbo
 */
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<ChatPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("----- 与服务器连接成功，请输入命令");
        new Thread(new ConsoleHandler(ctx)).start();
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatPacket msg) throws Exception {
//        log.info("chat ----- {}", JSON.toJSONString(msg));
        System.err.println(msg.getSendTime() + "\t" + msg.getFromUid() + ":" + msg.getFromUsername() + "发来消息\n" + msg.getMessage());
    }
}
