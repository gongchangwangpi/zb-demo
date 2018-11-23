package com.netty.im.v2;

import com.netty.im.codec.CodecHandler;
import com.netty.im.codec.PacketCodec;
import com.netty.im.protocol.ZBProtocol;
import com.netty.im.v2.handler.ChatClientHandler;
import com.netty.im.v2.handler.LoginClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 聊天客户端启动类
 * 
 * @author zhangbo
 */
public class ZBClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;
    
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ZBProtocol());
                        ch.pipeline().addLast(new CodecHandler(new PacketCodec()));
                        ch.pipeline().addLast(new LoginClientHandler());
                        ch.pipeline().addLast(new ChatClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
        channelFuture.channel().closeFuture().sync();
    }
    
}
