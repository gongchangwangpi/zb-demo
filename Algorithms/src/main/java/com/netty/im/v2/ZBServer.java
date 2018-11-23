package com.netty.im.v2;

import com.netty.im.codec.CodecHandler;
import com.netty.im.codec.PacketCodec;
import com.netty.im.protocol.ZBProtocol;
import com.netty.im.v2.handler.ChatServerHandler;
import com.netty.im.v2.handler.LoginServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                                .addLast(new ZBProtocol())
                                .addLast(new CodecHandler(new PacketCodec()))
//                                .addLast(new DecodeHandler(new PacketCodec()))
//                                .addLast(new EncodeHandler(new PacketCodec()))
//                                .addLast(new ServerHandler());
                                .addLast(new LoginServerHandler())
                                .addLast(new ChatServerHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(12345).sync();
        channelFuture.channel().closeFuture().sync();
    }
    
}
