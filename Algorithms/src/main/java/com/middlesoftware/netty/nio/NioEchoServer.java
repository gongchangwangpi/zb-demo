package com.middlesoftware.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioEchoServer {

    public static void main(String[] args) throws Exception {
        // 服务端处理线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 开启服务端Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 监听本地端口
        serverSocketChannel.bind(new InetSocketAddress(HostInfo.PORT));
        //
        Selector selector = Selector.open();
        // 注册感兴趣的事件，服务端监听接收连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("【NioEchoServer】服务端启动成功");

        int selectKey;
        while ((selectKey = selector.select()) > 0) {
            // 获取到监听的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    executorService.execute(new EchoServerHandler(clientChannel));
                    // 处理完之后移除
                    selectionKeyIterator.remove();
                }
            }
        }

        executorService.shutdown();
        serverSocketChannel.close();
    }

    private static class EchoServerHandler implements Runnable {
        private SocketChannel socketChannel;

        public EchoServerHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            boolean flag = true;
            int count = 0;
            // 申请缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                while (flag) {
                    // 先清除
                    buffer.clear();
                    // 读取消息
                    int readCount = socketChannel.read(buffer);
                    String readMessage = new String(buffer.array(), 0, readCount);
                    System.out.println("【NioEchoServer】收到消息：" + readMessage);
                    String writeMessage = "【ECHO】" + readMessage + (count++) + "\n";
                    if (Command.EXIT.equalsIgnoreCase(readMessage)) {
                        // 退出
                        flag = false;
                        writeMessage = "【EXIT】拜拜，下次再见";
                    }
                    // 清除缓冲区
                    buffer.clear();
                    buffer.put(writeMessage.getBytes("UTF-8"));
                    // 重置缓冲区
                    buffer.flip();
                    // 输出消息
                    socketChannel.write(buffer);
                    buffer.clear();
                }
                socketChannel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
