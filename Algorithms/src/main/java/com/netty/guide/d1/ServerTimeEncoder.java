package com.netty.guide.d1;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ServerTimeEncoder extends MessageToByteEncoder<MyTime> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, MyTime msg, ByteBuf out) throws Exception {
        byte[] bytes = JSON.toJSONBytes(msg);
        
        log.info("encode: length: {}", bytes.length);
        
        // 第一位为消息的长度，后面decode时同样的方法解析
        out.writeByte(bytes.length).writeBytes(bytes);
    }
}
