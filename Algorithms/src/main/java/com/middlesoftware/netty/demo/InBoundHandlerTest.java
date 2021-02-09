package com.middlesoftware.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Netty in bound handler 生命周期
 * 通道创建：handlerAdded - channelRegistered - channelActive -
 * 业务处理：channelRead - channelReadComplete -
 * 通道关闭：channelInactive - channelUnregistered - handlerRemoved
 *
 * @author bo6.zhang
 * @date 2021/2/9
 */
public class InBoundHandlerTest {

    public static void main(String[] args) throws Exception {

        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new InBound());
            }
        };

        // EmbeddedChannel用于测试
        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(2);

        channel.writeInbound(buffer);
        channel.flush();

        TimeUnit.SECONDS.sleep(2);

//        channel.writeInbound(buffer);
//        channel.flush();

        channel.close();

        TimeUnit.SECONDS.sleep(2);
    }

    @Slf4j
    private static class InBound extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelRegistered");
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelUnregistered");
            super.channelUnregistered(ctx);
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            log.info(" == handlerAdded");
            super.handlerAdded(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            log.info(" == handlerRemoved");
            super.handlerRemoved(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelActive");
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelInactive");
            super.channelInactive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info(" == channelRead");
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelReadComplete");
            super.channelReadComplete(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            log.info(" == userEventTriggered");
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            log.info(" == channelWritabilityChanged");
            super.channelWritabilityChanged(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info(" == exceptionCaught");
            super.exceptionCaught(ctx, cause);
        }
    }

}
