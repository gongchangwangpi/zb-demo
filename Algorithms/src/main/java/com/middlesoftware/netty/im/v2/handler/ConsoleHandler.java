package com.middlesoftware.netty.im.v2.handler;

import com.middlesoftware.netty.im.packet.ChatPacket;
import com.middlesoftware.netty.im.packet.LoginRequestPacket;
import com.middlesoftware.netty.im.util.Attributes;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Scanner;

/**
 * 从控制台读取用户键入的消息，发送到服务端
 * 
 * @author zhangbo
 */
@Slf4j
public class ConsoleHandler extends Thread {

    private ChannelHandlerContext ctx;
    
    public ConsoleHandler(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();
            log.info("console input: {}", next);
            if (StringUtils.isNotEmpty(next)) {
                if ("help".equalsIgnoreCase(next)) {
                    System.out.println("----- help -----");
                    System.out.println("login:uid:username");
                    System.out.println("----- help -----");
                } else if (next.startsWith("login")) {
                    // 登陆
                    String[] split = next.split(":");
                    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                    loginRequestPacket.setUid(split[1]);
                    loginRequestPacket.setUsername(split[2]);

                    Attribute<LoginRequestPacket> user = ctx.channel().attr(Attributes.USER);
                    user.set(loginRequestPacket);

                    ctx.channel().writeAndFlush(loginRequestPacket);
                    
                } else if (next.startsWith("chat")) {
                    String[] consoleMsg = next.split(":");
                    // 单聊
                    ChatPacket chatPacket = new ChatPacket();

                    Attribute<LoginRequestPacket> user = ctx.channel().attr(Attributes.USER);
                    LoginRequestPacket loginRequestPacket = user.get();
                    
                    chatPacket.setFromUid(loginRequestPacket.getUid());
                    chatPacket.setFromUsername(loginRequestPacket.getUsername());
                    chatPacket.setSendTime(new Date());
                    chatPacket.setToUid(consoleMsg[1]);
                    chatPacket.setToUsername(consoleMsg[2]);
                    chatPacket.setMessage(consoleMsg[3]);

                    ctx.channel().writeAndFlush(chatPacket);
                }
            }
        }
    }
}
