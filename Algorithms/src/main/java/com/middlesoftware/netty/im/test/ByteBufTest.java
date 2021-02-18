package com.middlesoftware.netty.im.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author zhangbo
 */
public class ByteBufTest {

    public static void main(String[] args) {

        // 设置是使用池化还是非池化 unpooled/pooled
        System.setProperty("io.netty.allocator.type", "unpooled");

        // 默认使用直接内存
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        // 使用heap内存，底层为byte[]，在未使用池化的时候，具有较快的分配和释放速度，直接在堆中分配，无需操作系统介入
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer(9, 100);
        // 使用操作系统直接内存，在网络IO等方面较快，不需在heap-direct-socket，直接在direct-socket，少一步copy的过程
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // 使用heap内存时，然后true，然后 array()方法返回对应的byte数组
        System.out.println("hasArray == " + buffer.hasArray());
        System.out.println();

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

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
