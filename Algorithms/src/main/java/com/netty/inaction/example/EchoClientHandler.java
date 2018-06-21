package com.netty.inaction.example;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by books on 2017/11/17.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    
    private Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client send msg");
        TimeUnit.SECONDS.sleep(1);
        ctx.write(Unpooled.copiedBuffer("netty rock", CharsetUtil.UTF_8));
//        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        logger.error(" client exceptionCaught", cause);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        logger.info("client receive: {}", ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
    }
}
