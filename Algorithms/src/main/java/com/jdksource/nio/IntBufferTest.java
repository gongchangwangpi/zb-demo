package com.jdksource.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;

/**
 * @author bo6.zhang
 * @date 2021/1/29
 */
@Slf4j
public class IntBufferTest {

    public static void main(String[] args) {

        // 分配内存
        IntBuffer buffer = IntBuffer.allocate(1024);

        log(buffer);
        //
        // errorDemo(buffer);

        // 正常模式，分配好内存后往里面写数据
        buffer.put(10);
        buffer.put(11);
        buffer.put(12);
        buffer.put(13);
        log(buffer);

        // flip翻转，其实就是重置 limit=position,position=0,便于后续的读操作从0开始
        buffer.flip();
        log(buffer);
        System.out.println(buffer.get());
        System.out.println(buffer.get());
//        System.out.println(buffer.get());
//        System.out.println(buffer.get());
        // 超过limit限制，报错
//        System.out.println(buffer.get());

        log(buffer);
        // 此处可手动重新设置position,或者limit，便于重复读取数据
        // 如果已经读完，不在需要重复读，可调用clear/compact，清空所有/已读的数据
//        buffer.clear();
//        log(buffer);

        buffer.compact();
        log(buffer);


    }

    private static void errorDemo(IntBuffer buffer) {
        System.out.println(buffer.get());
        log(buffer);
        System.out.println(buffer.get());
        log(buffer);

        buffer.put(10);
        log(buffer);
        buffer.position(buffer.position() - 1);
        log(buffer);

        System.out.println(buffer.get());
        log(buffer);
    }

    private static void log(IntBuffer buffer) {
        log.info("position = {}, limit = {}, capacity = {}", buffer.position(), buffer.limit(), buffer.capacity());
    }

}
