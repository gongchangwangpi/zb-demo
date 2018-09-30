package com.netty.qwzn.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 */
public class DelimiterServer {
    
    private static final String DELIMITER = "$$";
    
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(DELIMITER.getBytes())))
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(9090).sync();

            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    
    @Slf4j
    static class ServerHandler extends ChannelInboundHandlerAdapter {
        
        private static AtomicInteger count = new AtomicInteger();
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // addLast(new StringDecoder()) 添加StringDecoder,自动将ByteBuf类型的消息转换为字符串
//            ByteBuf byteBuf = (ByteBuf) msg;
//            byte[] bytes = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(bytes);
//            String request = new String(bytes);
            String request = (String) msg;

            String response = "server response " + count.getAndIncrement() + DELIMITER;
//            ctx.write(Unpooled.copiedBuffer(response.getBytes()));
            ctx.write(response);
            
            log.info("server read: {}, and write response: {}", request, response);
        }
        
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
            ctx.close().sync();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error("server exception", cause);
            ctx.channel().closeFuture().sync();
        }
    }
}
