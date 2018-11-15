package com.netty.im.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zhangbo
 */
public class ByteBufSliceTest {

    public static void main(String[] args) {

        ByteBuf buffer = Unpooled.buffer(10, 100);
        print("buffer", buffer);
        
        buffer.writeBytes("hello".getBytes());
        print("writeBytes", buffer);

        // slice,将原buffer中可读的数组新建了一个对象,底层和原buffer共用一个内存数组,新对象不可write,但可set
        ByteBuf slice = buffer.slice();
        print("slice original", buffer);
        print("slice new", slice);
//        slice.writeByte(12); // IndexOutOfBoundsException
        
        // 这两个改变会影响新的slice和原来的buffer
        slice.setByte(1, 85);
        buffer.setByte(2, 85);
        System.out.println(new String(slice.array()));
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
