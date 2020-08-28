package com.middlesoftware.netty.im.v2;

import com.middlesoftware.netty.im.codec.CodecHandler;
import com.middlesoftware.netty.im.codec.PacketCodec;
import com.middlesoftware.netty.im.protocol.ZBProtocol;
import com.middlesoftware.netty.im.v2.handler.ChatServerHandler;
import com.middlesoftware.netty.im.v2.handler.LoginServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 聊天服务端启动类
 * 
 * @author zhangbo
 */
public class ZBServer {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 自定义协议处理
                                .addLast(new ZBProtocol())
                                // 空闲连接检测
                                .addLast(new IdleStateHandler(180, 360, 360, TimeUnit.SECONDS))
                                // 自定义编码解码器
                                .addLast(new CodecHandler(new PacketCodec()))
//                                .addLast(new DecodeHandler(new PacketCodec()))
//                                .addLast(new EncodeHandler(new PacketCodec()))
//                                .addLast(new ServerHandler());
                                // 登陆请求处理
                                .addLast(new LoginServerHandler())
                                // 聊天请求处理
                                .addLast(new ChatServerHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(12345).sync();
        channelFuture.channel().closeFuture().sync();
    }
    
}
