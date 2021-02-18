package com.middlesoftware.netty.redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.redis.*;
import lombok.extern.slf4j.Slf4j;
import sun.net.util.IPAddressUtil;

import java.util.Scanner;

/**
 * @author bo6.zhang
 * @date 2021/2/18
 */
@Slf4j
public class NettyRedisClient {

    public static void main(String[] args) throws Exception {

        System.out.println("请输入Redis的 ip port:");

        Scanner scanner = new Scanner(System.in);

        String[] host = readHost(scanner);

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RedisDecoder(), new RedisEncoder(), new MyRedisDecode(scanner));
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(host[0], Integer.parseInt(host[1])).sync();

        log.info("NettyRedisClient start success...");

        // 阻塞直到关闭
        channelFuture.channel().closeFuture().sync();

    }

    private static String[] readHost(Scanner scanner) {
        while (true) {
            if (scanner.hasNext()) {
                String addr = scanner.nextLine();
                String[] address = addr.split(" ");
                if (address.length == 2) {
                    if (!IPAddressUtil.isIPv4LiteralAddress(address[0])) {
                        System.out.println("请输入正确的ip地址");
                    }
                    int port = 0;
                    try {
                        port = Integer.parseInt(address[1]);
                        if (port < 1 || port > 65535) {
                            System.out.println("请输入正确的端口号");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("请输入正确的端口号");
                    }
                    return address;
                } else {
                    System.out.println("请输入正确的host：10.10.110.110 6379");
                }
            }
        }
    }

    @Slf4j
    public static class MyRedisDecode extends ChannelInboundHandlerAdapter {

        private Scanner scanner;

        public MyRedisDecode(Scanner scanner) {
            this.scanner = scanner;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("channelActive");

            new Thread(() -> {
                while (true) {
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        InlineCommandRedisMessage message = new InlineCommandRedisMessage(line);
                        ctx.writeAndFlush(message);
                    }
                }
            }, "Scanner-Thread").start();

            super.channelActive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            if (msg instanceof RedisMessage) {
                if (msg instanceof BulkStringHeaderRedisMessage) {
                    BulkStringHeaderRedisMessage message = (BulkStringHeaderRedisMessage) msg;
                    log.info("receive BulkStringHeaderRedisMessage.bulkStringLength() {}", message.bulkStringLength());
                } else if (msg instanceof DefaultLastBulkStringRedisContent) {
                    DefaultLastBulkStringRedisContent content = (DefaultLastBulkStringRedisContent) msg;
                    ByteBuf byteBuf = content.content();
                    byte[] bytes = new byte[byteBuf.readableBytes()];
                    byteBuf.readBytes(bytes);
                    log.info("receive DefaultLastBulkStringRedisContent {}", new String(bytes));
                } else {

                    log.info("receive redis resp: {}", msg);
                }
            }

            super.channelRead(ctx, msg);
        }
    }
}
