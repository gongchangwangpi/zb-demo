package com.netty.im.v2.handler;

import com.netty.im.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhangbo
 */
public class DecodeHandler extends ByteToMessageDecoder {
    
    private PacketCodec packetCodec;

    public DecodeHandler(PacketCodec packetCodec) {
        this.packetCodec = packetCodec;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(packetCodec.decode(in));
    }
}
