package com.jdksource.nio.chapter2;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建ByteBuffer的CharBuffer视图
 * 
 * @author zhangbo
 */
@Slf4j
public class ByteBufferCharViewTest {

    public static void main(String[] args) {

        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
//        byteBuffer.put((byte) 'H');
//        byteBuffer.put((byte) 'e');
//        byteBuffer.put((byte) 'l');
//        byteBuffer.put((byte) 'l');
//        byteBuffer.put((byte) 'o');
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 'H');
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 'i');
        byteBuffer.put((byte) 0);
        
        byteBuffer.flip();
        
        log.info("after put -->> position: {}, limit: {}, capacity: {}", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());

        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        int remaining = byteBuffer.remaining();
        for (int i = 0; i < remaining; i++) {
            System.out.print(byteBuffer.get());
        }
        System.out.println();
        
        log.info("after put -->> position: {}, limit: {}, capacity: {}", charBuffer.position(), charBuffer.limit(), charBuffer.capacity());

        remaining = charBuffer.remaining();
        for (int i = 0; i < remaining; i++) {
            System.out.print(charBuffer.get());
        }


    }
    
}
