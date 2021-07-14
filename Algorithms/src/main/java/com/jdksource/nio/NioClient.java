package com.jdksource.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author bo6.zhang
 * @date 2021/1/29
 */
@Slf4j
public class NioClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9922));

        while (true) {

            if (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    SocketChannel socketChannel2 = (SocketChannel) selectionKey.channel();
                    socketChannel2.configureBlocking(false);
                    if (selectionKey.isReadable()) {

                        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                        if (socketChannel2.read(byteBuffer) > 0) {
                            log.info(" =====>>>> read message: {}", new String(byteBuffer.array()));
                        } else {
                            log.info(" ===== isReadable");
                        }
                        socketChannel2.register(selector, SelectionKey.OP_WRITE);
                    } else if (selectionKey.isWritable()) {

                        log.info(" ===== isWritable");
                        socketChannel2.write(ByteBuffer.wrap("hello, nio client dadada".getBytes()));
                        socketChannel2.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isConnectable()) {

                        log.info(" ===== isConnectable");
                        socketChannel2.finishConnect();
                        socketChannel2.write(ByteBuffer.wrap("hello, nio client connect".getBytes()));
                        socketChannel2.register(selector, SelectionKey.OP_WRITE);
                    }

                    iterator.remove();
                    TimeUnit.MILLISECONDS.sleep(2500);
                }
            }
        }

    }

}
