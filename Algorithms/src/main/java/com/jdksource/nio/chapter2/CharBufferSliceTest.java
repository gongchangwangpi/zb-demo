package com.jdksource.nio.chapter2;

import java.nio.CharBuffer;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link CharBuffer#slice()} 和 {@link CharBuffer#duplicate()}
 * 两个方法都是共用原有的底层char[]，同时也会继承readonly等属性
 * 也可以用{@link CharBuffer#asReadOnlyBuffer()}来生成一个只读的视图
 * 
 * @author zhangbo
 */
@Slf4j
public class CharBufferSliceTest {

    public static void main(String[] args) {

        char[] chars = "hello world".toCharArray();

        CharBuffer charBuffer = CharBuffer.wrap(chars);
        
        charBuffer.position(1).limit(5);

        // 剪切buffer，和原buffer公用一个char[]，只是新的slice的capacity已固定，所以不能访问其他的数据
        CharBuffer slice = charBuffer.slice();

        log.info("slice -->> position: {}, limit: {}, capacity: {}", slice.position(), slice.limit(), slice.capacity());
        
        // 对新的buffer的修改，同样会映射到原来的buffer
        slice.put(0, 'X');
        
        int remaining = slice.remaining();
        for (int i = 0; i < remaining; i++) {
            System.out.print(slice.get());
        }
        System.out.println();

        // 将原buffer恢复为刚wrap时的初始状态
        charBuffer.limit(charBuffer.capacity()).position(0);
        
        int remaining1 = charBuffer.remaining();
        for (int i = 0; i < remaining1; i++) {
            System.out.print(charBuffer.get());
        }

    }
    
}
