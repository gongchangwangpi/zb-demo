package com.middlesoftware.netty.demo;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考 rocketmq
 *
 * @author bo6.zhang
 * @date 2021/2/8
 */
@Slf4j
public class MyNettyServer {

    public static void main(String[] args) throws Exception {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("BOSS-"));
        NioEventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("WORKER-"));

        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .localAddress(10010)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info(" === initChannel === ");
                            socketChannel.pipeline()
                                    .addLast(new MyNettyEncoder(), new MyNettyDecoder(), new MyNettyServerHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            log.info(" === server start === port: {}", channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    @Slf4j
    private static class MyNettyServerHandler extends SimpleChannelInboundHandler<MyRequest> {

        private static final AtomicInteger count = new AtomicInteger();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MyRequest msg) throws Exception {
            // 接收客户端请求
            log.info(" === read msg: {}", JSON.toJSONString(msg));
            // 业务处理，还可以使用自定义线程池进行业务处理
            msg.getBody().setName(msg.getBody().getName() + count.incrementAndGet());
            // 返回相应内容
            ctx.writeAndFlush(msg);
        }
    }

}
