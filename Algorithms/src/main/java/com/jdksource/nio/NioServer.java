package com.jdksource.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author bo6.zhang
 * @date 2021/1/29
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9922));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        log.info(" === nio server start ok ===");

        while (true) {
            if (selector.select() > 0) {

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                        if (socketChannel.read(byteBuffer) > 0) {
                            log.info(" =====>>>> read message: {}", new String(byteBuffer.array()));
                        } else {
                            log.info(" ===== isReadable");
                        }
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                    } else if (selectionKey.isWritable()) {

                        log.info(" ===== isWritable");

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("hello, nio server write message".getBytes()));
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isAcceptable()) {

                        log.info(" ===== isAcceptable");

                        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel accept = socketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }

                    iterator.remove();
                    TimeUnit.SECONDS.sleep(1);
                }
            }

        }

    }

    private static class Handler implements Runnable {
        private SocketChannel socketChannel;

        public Handler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {

        }
    }

}
