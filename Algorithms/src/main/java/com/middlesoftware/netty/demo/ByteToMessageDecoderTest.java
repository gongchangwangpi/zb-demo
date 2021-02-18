package com.middlesoftware.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/2/18
 */
public class ByteToMessageDecoderTest {

    public static void main(String[] args) {

        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new ByteToIntegerDecoder(), new IntegerProcess());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);

        for (int i = 0; i < 100; i++) {
            embeddedChannel.writeInbound(i);
        }

    }

    public static class ByteToIntegerDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            while (in.readableBytes() >= 4) {
                // integer 4字节
                out.add(in.readInt());
            }
        }
    }

    @Slf4j
    public static class IntegerProcess extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info(" read : {}", msg);
            super.channelRead(ctx, msg);
        }
    }
}
