package com.middlesoftware.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * 参考 rocketmq
 *
 * @author bo6.zhang
 * @date 2021/2/9
 */
public class MyNettyDecoder extends LengthFieldBasedFrameDecoder {

    public MyNettyDecoder() {
        super(65535, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 调用父类方法处理粘包拆包等问题
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        ByteBuffer byteBuffer = frame.nioBuffer();
        // 自定义协议解码
        return MyRequest.decode(byteBuffer);
    }
}
