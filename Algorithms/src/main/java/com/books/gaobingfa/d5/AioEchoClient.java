package com.books.gaobingfa.d5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by books on 2017/5/9.
 */
public class AioEchoClient {

    public static void main(String[] args) throws Exception {

        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        // connect的时候,注册CompletionHandler完成时处理器,当连接成功的时候,开始往服务器写数据
        // connect方法立即返回,不会阻塞. 当监听到连接成功时,会调用CompletionHandler.completed
        client.connect(new InetSocketAddress("localhost", 8000), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                // write的时候,注册CompletionHandler完成时处理器,当写入数据后,开始读取服务器的数据
                // write方法立即返回,不会阻塞. 当监听到写入成功时,会调用CompletionHandler.completed
                client.write(ByteBuffer.wrap("Hello!".getBytes()), null, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            // 当读取的时候,注册CompletionHandler,即读取完成时,会收到通知来处理读取的数据
                            // 读的时候注册,异步通知,不会阻塞
                            client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer buffer) {
                                    buffer.flip();
                                    System.out.println(new String(buffer.array()));
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
            }
        });
        //由于主线程马上结束，这里等待上述处理全部完成
        Thread.sleep(1000);
    }

}
