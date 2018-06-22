package com.netty.guide;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 时间服务器，因不需要读客户端的数据，
 * 所以在连接建立好时，直接输出当前时间戳，然后关闭连接
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当连接建立好时，调用该方法，输出当前时间戳
     * 
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive -->>  {}", ctx.toString());
        
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
        // 连接只使用一次即关闭
//        f.addListener((future) -> ctx.close());
//        f.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("出错啦", cause);
        ctx.close();
    }
}
