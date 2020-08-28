package com.middlesoftware.netty.im.protocol;

import com.middlesoftware.netty.im.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义协议解析器
 * 
 * 自定义协议格式为：
 * 魔数 + 版本号 + 序列化算法 + 操作类型 + 数据报长度 + 数据报
 * 4byte   1byte   1byte       1byte      4byte     ...
 * 
 * @author zhangbo
 */
@Slf4j
public class ZBProtocol extends LengthFieldBasedFrameDecoder {
    
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;
    
    private static final int MAGIC_NUM = 0x12345678;

    public ZBProtocol() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 非本协议的连接，直接关闭
        if (in.getInt(in.readerIndex()) != MAGIC_NUM) {
            log.error(" -- 非自定义协议，强制关闭连接");
            ctx.channel().close();
            return null;
        }
        Packet decode = new PacketCodec().decode(in);
        return decode;
    }
}
