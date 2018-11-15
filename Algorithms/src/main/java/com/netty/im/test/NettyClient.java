package com.netty.im.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
@Slf4j
public class NettyClient {
    
    private static final int MAX_RETRY = 5;
    
    private static int retry = 0;
    
    private static final long DEFAULT_INTERVAL = 1;

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder())
                                .addLast(new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                                        ctx.write("hello netty");
                                    }
                                });
                    }
                });

        ChannelFuture channelFuture = connect(bootstrap, "127.0.0.1", 3306);

        channelFuture.channel().closeFuture().sync();

    }

    private static ChannelFuture connect(Bootstrap bootstrap, final String host, int port) throws InterruptedException {
        return bootstrap.connect("127.0.0.1", 1000).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("netty client connect success, connect port: {}", port);
                    } else {
                        // 
                        if (retry++ < MAX_RETRY) {
                            TimeUnit.SECONDS.sleep(DEFAULT_INTERVAL * (1 << retry));
                            log.info("netty client connect fail, auto reconnect");
                            connect(bootstrap, host, port + 1);
                        } else {
                            log.info("netty client connect fail, reconnect: {}, will close...", retry);
                        }
                    }
                }
            });
    }

}
