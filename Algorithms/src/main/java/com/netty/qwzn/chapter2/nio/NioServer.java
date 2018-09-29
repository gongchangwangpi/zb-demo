package com.netty.qwzn.chapter2.nio;

import com.netty.qwzn.chapter2.bio.TimeClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以继续使用{@link TimeClient}测试此服务端
 * 
 * @author zhangbo
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.bind(new InetSocketAddress(8080));
            
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            
            while (true) {
                int select = selector.select();
                if (select == 0) {
                    continue;
                }
                executorService.execute(new Handler(selector));
            }
        } finally {
            IOUtils.closeQuietly(serverSocketChannel);
            executorService.shutdown();
        }
    }
    
    static class Handler implements Runnable {
        Selector selector;
        public Handler(Selector selector) {
            this.selector = selector;
        }
        
        @Override
        public void run() {
            try {
                log.info("server handler run...");
                
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    log.info("request: {}", IOUtils.toString(byteBuffer.array()));

                    iterator.remove();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
