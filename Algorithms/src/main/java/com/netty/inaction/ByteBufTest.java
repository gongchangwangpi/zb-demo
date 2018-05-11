package com.netty.inaction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by books on 2017/11/21.
 */
public class ByteBufTest {

    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.buffer(16);

        for (int i = 0; i < 16; i++) {
            byteBuf.writeByte(i + 1);
        }

        for (int i = 0; i < 16; i++) {
            System.out.println(byteBuf.getByte(i));
        }

        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        
        byteBuf.clear();
        System.out.println(byteBuf.readByte());
    }
    
}
