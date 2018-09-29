package com.netty.qwzn.chapter2.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangbo
 */
@Slf4j
public class NioClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        if (socketChannel.connect(new InetSocketAddress("localhost", 8080))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }

        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            
//            log.info("selected keys...");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                SocketChannel channel = (SocketChannel) selectionKey.channel();

                if (selectionKey.isConnectable()) {
                   
                    if (channel.isConnected()) {
                        channel.register(selector, SelectionKey.OP_READ);

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.put("hello nio".getBytes());
                        channel.write(byteBuffer);
                        log.info("write request");
                    } else {
                        log.error("connect fail");
                        System.exit(1);
                    }
                }
                
                if (selectionKey.isReadable()) {

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);

                    log.info("response: {}", IOUtils.toString(byteBuffer.array()));
                }
                
                iterator.remove();
            }
        }


    }
    
}
