package com.middlesoftware.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Netty out bound handler 生命周期
 * 通道创建：handlerAdded - channelRegistered - channelActive -
 * 业务处理：channelRead - channelReadComplete -
 * 通道关闭：channelInactive - channelUnregistered - handlerRemoved
 *
 * @author bo6.zhang
 * @date 2021/2/9
 */
public class OutBoundHandlerTest {

    public static void main(String[] args) throws Exception {

        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new OutBound());
            }
        };

        // EmbeddedChannel用于测试
        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(2);

        channel.writeOutbound(buffer);
//        channel.flush();

        TimeUnit.SECONDS.sleep(2);

//        channel.writeInbound(buffer);
//        channel.flush();

        channel.close();

        TimeUnit.SECONDS.sleep(2);
    }

    private static String getExecutingMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stackTrace[3];
        return e.getMethodName();
    }

    @Slf4j
    private static class OutBound extends ChannelOutboundHandlerAdapter {

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.bind(ctx, localAddress, promise);
        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.disconnect(ctx, promise);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.close(ctx, promise);
        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.deregister(ctx, promise);
        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.read(ctx);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.write(ctx, msg, promise);
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.flush(ctx);
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.handlerAdded(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.handlerRemoved(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info(" == {}", getExecutingMethodName());
            super.exceptionCaught(ctx, cause);
        }
    }

}
