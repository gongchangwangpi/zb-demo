package com.middlesoftware.netty.im.codec;

import com.middlesoftware.netty.im.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhangbo
 */
@Slf4j
public class CodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    
    private PacketCodec packetCodec;

    public CodecHandler(PacketCodec packetCodec) {
        this.packetCodec = packetCodec;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
//        log.info(" -- encode: {}", JSON.toJSONString(msg));
        ByteBuf byteBuf = packetCodec.encode(ctx.alloc(), msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Packet decode = packetCodec.decode(msg);
        out.add(decode);
//        log.info(" -- decode: {}", JSON.toJSONString(decode));
    }
}
