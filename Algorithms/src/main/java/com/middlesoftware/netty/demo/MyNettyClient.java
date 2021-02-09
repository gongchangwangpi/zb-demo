package com.middlesoftware.netty.demo;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bo6.zhang
 * @date 2021/2/8
 */
@Slf4j
public class MyNettyClient {

    public static void main(String[] args) throws Exception {

        Bootstrap serverBootstrap = new Bootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("BOSS-"));

        try {
            serverBootstrap.group(boss)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info(" === initChannel === ");
                            socketChannel.pipeline()
                                    .addLast(new MyNettyEncoder(), new MyNettyDecoder(), new MyNettyClientHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.connect(new InetSocketAddress("127.0.0.1", 10010)).sync();
            log.info(" === client start === port: {}", channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
        }

    }


    private static final AtomicInteger count = new AtomicInteger();
    private static MyRequest getMyRequest() {
        MyRequest myRequest = new MyRequest();

        MyRequest.Header header = new MyRequest.Header();
        header.setCode(count.get());
        header.setVersion(count.get());
        header.setProtocol(count.get());
        myRequest.setHeader(header);

        MyRequest.Body body = new MyRequest.Body();
        body.setId((long) count.get());
        body.setName("name" + count.get());
        body.setAddr("成都" + count.get());
        body.setAge(count.get());
        body.setCreateTime(LocalDateTime.now());
        myRequest.setBody(body);

        count.getAndIncrement();
        return myRequest;
    }

    @Slf4j
    private static class MyNettyClientHandler extends SimpleChannelInboundHandler<MyRequest> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 连接服务端成功，发送请求
            MyRequest myRequest = getMyRequest();
            ctx.writeAndFlush(myRequest);

            new Reader(ctx).start();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MyRequest msg) throws Exception {
            // 接收服务端响应
            log.info(" === read msg: {}", JSON.toJSONString(msg));
            TimeUnit.MILLISECONDS.sleep(500);

//            MyRequest myRequest = getMyRequest();
//            ctx.writeAndFlush(myRequest);
        }
    }

    private static class Reader {
        private ChannelHandlerContext ctx;
        private Scanner scanner;
        public Reader(ChannelHandlerContext ctx) {
            this.ctx = ctx;
            this.scanner = new Scanner(System.in);
        }
        public void start() {
            Thread clientReader = new Thread(() -> {
                System.out.println("请输入name:\n");
                while (true) {
                    while (scanner.hasNext()) {
                        // 读取用户在终端输入的内容
                        MyRequest myRequest = getMyRequest();
                        String name = scanner.next();
                        myRequest.getBody().setName(name);
                        // 发送到服务端
                        ctx.writeAndFlush(myRequest);
                    }
                }
            }, "ClientReader");
            clientReader.start();
        }
    }

}
