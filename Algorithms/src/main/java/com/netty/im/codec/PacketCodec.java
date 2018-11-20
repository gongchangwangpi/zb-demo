package com.netty.im.codec;

import com.netty.im.protocol.Command;
import com.netty.im.protocol.Packet;
import com.netty.im.packet.LoginRequestPacket;
import com.netty.im.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author zhangbo
 */
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
        
        return Serializer.DEFAULT.deserialize(bytes, type);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return Command.getRequestType(command);
    }

    public static void main(String[] args) throws Exception {

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUid("u123");
        packet.setUsername("name");
        packet.setPassword("12345");

        PacketCodec codec = new PacketCodec();
        ByteBuf byteBuf = codec.encode(ByteBufAllocator.DEFAULT, packet);

        LoginRequestPacket decode = codec.decode(byteBuf);
        System.out.println(decode);
        
    }
}
