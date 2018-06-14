package com.jdksource.nio.chapter2;

import java.nio.CharBuffer;

/**
 * 对缓冲区的填充和释放
 * 
 * @author zhangbo
 */
public class BufferFillDrain {

    public static void main(String[] argv) throws Exception {
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            buffer.flip();
            drainBuffer(buffer);
            // clear并不真正清空缓冲区，只是将position，limit，mark置为初始状态
            // position = 0;
            // limit = capacity;
            // mark = -1;
            buffer.clear();
        }
    }

    /**
     * 释放
     * @param buffer
     */
    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
    }

    /**
     * 填充
     * @param buffer
     * @return
     */
    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return (false);
        }
        String string = strings[index++];
        for (int i = 0; i < string.length(); i++) {
            buffer.put(string.charAt(i));
        }
//        缓冲区并不是多线程安全的。如果您想以多线程同时存取特定
//        的缓冲区，您需要在存取缓冲区之前进行同步（例如对缓冲区
//        对象进行跟踪）
        return (true);
    }

    private static int index = 0;
    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
            "Help Me! Help Me!",
    };

}
