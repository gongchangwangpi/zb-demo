package com.middlesoftware.netty.im.v2.handler;

import com.middlesoftware.netty.im.packet.ChatPacket;
import com.util.DateUtil;
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
        System.err.println("----- 与服务器连接成功，请输入命令 -----\n登陆\tlogin:uid:username\n聊天\tchat:uid:user:message");
        new Thread(new ConsoleHandler(ctx)).start();
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatPacket msg) throws Exception {
        System.err.println(DateUtil.defaultFormatDateTime(msg.getSendTime()) + "\t" + msg.getFromUid() + ":" + msg.getFromUsername() + "\t发来消息:\n" + msg.getMessage());
    }
}
