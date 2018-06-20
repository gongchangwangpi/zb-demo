package com.jdksource.nio.chapter4;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class MyEchoClient {

    private static String host = "127.0.0.1";
//    private static int port = 6789;
    private static int port = 1234;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        
        while (true) {

            int select = selector.select();
            if (select == 0) {
                continue;
            }
            
            if (selectionKey.isReadable()) {
                SocketChannel channel = (SocketChannel) selectionKey.channel();

                int count;
                buffer.clear();
                while ((count = socketChannel.read(buffer)) > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        log.info("read from serverSocket: {}", new String(bytes));
//                        socketChannel.write(buffer);
                    }
                    buffer.clear();
                }
                
                socketChannel.close();
            }
            
        }

    }
    
}
