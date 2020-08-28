package com.middlesoftware.netty.im.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zhangbo
 */
public class ByteBufCopyTest {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10, 100);
        print("buffer", buffer);
        
        buffer.writeBytes("hello".getBytes());
        print("writeBytes", buffer);

        // copy,新建一个对象,新建了一个和原buffer一模一样的buffer,里面的数据一样,
        // 但writeIndex/readIndex/capacity等都不一样
        // 底层的内存数组不一样,也是全新分配的
        ByteBuf copy = buffer.copy();
        print("copy original", buffer);
        print("copy new", copy);
        
        // 此处的改变仅会影响到自己
        copy.writeByte(80);
        buffer.writeByte(79);
        buffer.writeByte(79);
        copy.setByte(1, 85);
        System.out.println(new String(copy.array()));
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
