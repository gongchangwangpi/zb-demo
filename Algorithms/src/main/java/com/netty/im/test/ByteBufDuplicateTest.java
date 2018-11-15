package com.netty.im.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zhangbo
 */
public class ByteBufDuplicateTest {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10, 100);
        print("buffer", buffer);
        
        buffer.writeBytes("hello".getBytes());
        print("writeBytes", buffer);

        // duplicate,新建一个对象，但底层和原ByteBuf共用一个内存,两个对象分别维护自己的readIndex和writeIndex等
        ByteBuf duplicate = buffer.duplicate();
        print("duplicate original", buffer);
        print("duplicate new", duplicate);
        
        // 此处的改变会影响到两个,但由于两个对象各自维护自己的writeIndex和readIndex,
        // 所以后面的buffer.write会覆盖掉duplicate.write
        duplicate.writeByte(80);
        buffer.writeByte(79);
        buffer.writeByte(79);
        duplicate.setByte(1, 85);
        System.out.println(new String(duplicate.array()));
        System.out.println(new String(buffer.array()));
        
    }
    
    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
