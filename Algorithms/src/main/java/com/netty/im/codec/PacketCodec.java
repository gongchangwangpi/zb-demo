package com.netty.im.codec;

import com.alibaba.fastjson.JSON;
import com.netty.im.protocol.Command;
import com.netty.im.protocol.Packet;
import com.netty.im.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class PacketCodec {
    
    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) throws Exception {

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        ByteBuf buffer = alloc.directBuffer();
        // 先写入协议头信息
        buffer.writeInt(packet.magicNum());
        buffer.writeByte(packet.version());
        buffer.writeByte(packet.command());
        buffer.writeInt(bytes.length);
        // 真正的数据包
        buffer.writeBytes(bytes);
        log.info("----->>> encode : magicNum = {}, version = {}, command = {}, length = {}, Object = {}", packet.magicNum(), packet.version(), packet.command(), bytes.length, JSON.toJSONString(packet));
        return buffer;
    }

    public <T> T decode(ByteBuf byteBuf) throws Exception {

        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        
        Class<? extends Packet> type = getRequestType(command);

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        T o = Serializer.DEFAULT.deserialize(bytes, type);
        log.info("----->>> decode : magicNum = {}, version = {}, command = {}, length = {}, Object = {}", magicNum, version, command, length, JSON.toJSONString(o));
        return o;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return Command.getRequestType(command);
    }

}
