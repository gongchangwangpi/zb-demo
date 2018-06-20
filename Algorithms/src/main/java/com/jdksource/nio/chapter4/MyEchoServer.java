package com.jdksource.nio.chapter4;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class MyEchoServer {
    
    private static String host = "127.0.0.1";
    private static int port = 6789;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        log.info("listening on port: {}", port);

        serverSocketChannel.socket().bind(new InetSocketAddress(port));


        Selector selector = Selector.open();

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        while (true) {

            int select = selector.select();
            log.info("select: {}", select);
            if (select == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            
            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                

                if (selectionKey.isAcceptable()) {
                    // 安全的从selectionKey上取
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    server.register(selector, SelectionKey.OP_ACCEPT);

                    log.info("accept socket: {}, and write data...", socketChannel);

                    buffer.clear();
                    buffer.put(("this is MyEchoServer, time is : " + new Date()).getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                }

                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    int count;
                    buffer.clear();
                    while ((count = socketChannel.read(buffer)) > 0) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < buffer.remaining(); i++) {
                                sb.append(buffer.get());
                            }
                            log.info("read from socket: {}", sb);
//                        socketChannel.write(buffer);
                        }
                        buffer.clear();
                    }

                }
                
                iterator.remove();
            }
            
        }


    }
    
}
