package com.middlesoftware.netty.guide.d1;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class MyTimeCodec extends ByteToMessageCodec<MyTime> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, MyTime msg, ByteBuf out) throws Exception {
        byte[] bytes = JSON.toJSONBytes(msg);

        log.info("encode length: {}", bytes.length);

        // 第一位为消息的长度，后面decode时同样的方法解析
        out.writeByte(bytes.length).writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();
        log.info("decode length: {}", length);
        if (in.readableBytes() < length + 4) {
            return;
        }
        ByteBuf buf = Unpooled.buffer(length);
        in.readBytes(buf, 4, length);
        out.add(JSON.parse(buf.array()));
    }
}
