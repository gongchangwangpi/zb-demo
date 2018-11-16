package com.netty.im.v2.handler;

import com.netty.im.request.LoginRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
            String next = scanner.next();
            log.info("console input: {}", next);
            if (StringUtils.isNotEmpty(next)) {
                if ("help".equalsIgnoreCase(next)) {
                    System.out.println("----- help -----");
                    System.out.println("login:uid:username");
                    System.out.println("----- help -----");
                } else if (next.startsWith("login")) {
                    String[] split = next.split(":");
                    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                    loginRequestPacket.setUid(split[1]);
                    loginRequestPacket.setUsername(split[2]);
                    ctx.channel().writeAndFlush(loginRequestPacket);
                }
            }
        }
    }
}
