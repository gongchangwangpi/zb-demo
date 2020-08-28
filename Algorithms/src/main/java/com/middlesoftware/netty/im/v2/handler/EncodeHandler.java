package com.middlesoftware.netty.im.v2.handler;

import com.middlesoftware.netty.im.codec.PacketCodec;
import com.middlesoftware.netty.im.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangbo
 */
public class EncodeHandler extends MessageToByteEncoder<Packet> {

    private PacketCodec packetCodec;

    public EncodeHandler(PacketCodec packetCodec) {
        this.packetCodec = packetCodec;
    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        out.writeBytes(packetCodec.encode(ctx.alloc(), msg));
    }
}
