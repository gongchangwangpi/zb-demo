package com.jdksource.nio.chapter2;

import java.nio.CharBuffer;

import lombok.extern.slf4j.Slf4j;

/**
 * 压缩
 * 
 * @author zhangbo
 */
@Slf4j
public class BufferCompactTest {

    public static void main(String[] args) {

        CharBuffer charBuffer = CharBuffer.allocate(1024);

        charBuffer.put('H');
        charBuffer.put('e');
        charBuffer.put('l');
        charBuffer.put('l');
        charBuffer.put('o');
        charBuffer.put(' ');
        charBuffer.put('W');
        charBuffer.put('o');
        charBuffer.put('r');
        charBuffer.put('l');
        charBuffer.put('d');
        charBuffer.put('!');

        log.info("after put -->> position: {}, limit: {}, capacity: {}", charBuffer.position(), charBuffer.limit(), charBuffer.capacity());

        charBuffer.flip();
        
        log.info("after flip -->> position: {}, limit: {}, capacity: {}", charBuffer.position(), charBuffer.limit(), charBuffer.capacity());

        for (int i = 0; i < 5; i++) {
            char c = charBuffer.get();
            System.out.print(c);
        }
        System.out.println();

        log.info("after get -->> position: {}, limit: {}, capacity: {}", charBuffer.position(), charBuffer.limit(), charBuffer.capacity());
        // 压缩，将已经读取的内容释放，将后面未读取的复制到最前面的位置
        // 但复制后剩余的位置还是之前的值
        // 一般来说，compact压缩后，是为了继续写入数据
        charBuffer.compact();
        log.info("after compact -->> position: {}, limit: {}, capacity: {}", charBuffer.position(), charBuffer.limit(), charBuffer.capacity());

        // 此处强制将position设置为0，是为了后面方便查看buffer中的数据
//        charBuffer.position(0);
        
        for (int i = 0; i < 1024 - charBuffer.position(); i++) {
            char c = charBuffer.get();
            System.out.print(c);
        }
        
    }
    
}
