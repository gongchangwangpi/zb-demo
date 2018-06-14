package com.jdksource.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author zhangbo
 */
public class ByteBufferOrderTest {

    public static void main(String[] args) {

        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{74, 56, 85, 127});

        System.out.println(byteBuffer.order());

        int remaining = byteBuffer.remaining();
        for (int i = 0; i < remaining; i++) {
            System.out.print(byteBuffer.get());
        }
        System.out.println();
        
        byteBuffer.position(0);
        
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        remaining = byteBuffer.remaining();
        for (int i = 0; i < remaining; i++) {
            System.out.print(byteBuffer.get());
        }

    }
    
}
