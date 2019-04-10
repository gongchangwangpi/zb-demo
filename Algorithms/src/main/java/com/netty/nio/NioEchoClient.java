package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioEchoClient {

    public static void main(String[] args) throws Exception {
        // 开启客户端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HostInfo.LOCALHOST, HostInfo.PORT));

        boolean flag = true;
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (flag) {
            // 读取控制台输入信息
            System.out.print("【NioEchoClient】客户端已启动，请输入消息：");
            Scanner scanner = new Scanner(System.in);
            String inputMessage = scanner.next();

            buffer.clear();
            buffer.put(inputMessage.getBytes("UTF-8"));
            buffer.flip();
            socketChannel.write(buffer);

            buffer.clear();
            int readCount = socketChannel.read(buffer);
            String readMessage = new String(buffer.array(), 0, readCount);
            System.err.println("【NioEchoClient】收到响应：" + readMessage);

            buffer.clear();
            if (Command.EXIT.equalsIgnoreCase(inputMessage)) {
                flag = false;
            }
        }

        socketChannel.close();
    }

}
