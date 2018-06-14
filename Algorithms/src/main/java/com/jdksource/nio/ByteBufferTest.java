package com.jdksource.nio;

import java.nio.ByteBuffer;

/**
 * @author zhangbo
 */
public class ByteBufferTest {

    public static void main(String[] args) {

//        allocateTest();

//        wrapTest();

//        someTest();
        
    }

    private static void someTest() {
        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.put((byte) 'H');
        byteBuffer.put((byte) 'e');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'o');

        byteBuffer.flip();

        int remaining = byteBuffer.remaining();
        System.out.println("count: " + remaining);

        for (int i = 0; i < remaining; i++) {
            System.out.print((char) byteBuffer.get());
        }

        System.out.println();
        byteBuffer.limit(capacity);
//        byteBuffer.mark();

        byteBuffer.put((byte) ' ');
        byteBuffer.put((byte) 'W');
        byteBuffer.put((byte) 'o');
        byteBuffer.put((byte) 'r');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'd');

        byteBuffer.flip();

        remaining = byteBuffer.remaining();
        System.out.println("count: " + remaining);

        for (int i = 0; i < remaining; i++) {
            System.out.print((char) byteBuffer.get());
        }
    }

    private static void wrapTest() {
        byte[] bytes = "Hello World".getBytes();

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        // put导致position改变，后面的get也会从改变后的position开始读，而不是从0开始读
        byteBuffer.put((byte) 'M');
        byteBuffer.put((byte) 'x');

        // 绝对位置写，不会改变position
        byteBuffer.put(5, (byte) ',');

        byte b = byteBuffer.get();
        System.out.println((char) b);
        b = byteBuffer.get();
        System.out.println((char) b);
        
        // 因为wrap的Buffer，已经设置了limit，所以超出了，抛异常IndexOutOfBoundsException
//        b = byteBuffer.get(512);
//        System.out.println(b);
    }

    private static void allocateTest() {
        int capacity = 1024;

        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);

        byteBuffer.put((byte) 'H');
        byteBuffer.put((byte) 'e');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'o');
        byteBuffer.put((byte) ' ');
        byteBuffer.put((byte) 'W');
        byteBuffer.put((byte) 'o');
        byteBuffer.put((byte) 'r');
        byteBuffer.put((byte) 'l');
        byteBuffer.put((byte) 'd');

        // 使用绝对位置写，不会改变当前的position，后面的！还是会拼接在最后
        byteBuffer.put(5, (byte) ',').put((byte) '!');

        byteBuffer.flip();
        // 两次flip会导致limit和position都变为0，导致不可读，不可写
//        byteBuffer.flip();
        
        // reset通常和mark方法一起使用，mark标记当前的position，reset则将position设置为mark的值
        // 如果mark没有初始化，为-1时，抛异常，因position不能小于0
//        byteBuffer.reset();

        if (byteBuffer.hasArray()) {
            byte[] bytes = byteBuffer.array();
            String str = new String(bytes);
            System.out.println(str);
        }

        byte b = byteBuffer.get();
        System.out.println((char) b);

        // rewind后退，可以重复读
        byteBuffer.rewind();
        b = byteBuffer.get();
        System.out.println((char) b);

        // 如果没有执行flip，则输出0，因为底层的byte[]初始化各个位置的数据为0
        // 如果已经执行了flip，则设置过limit，抛异常
        b = byteBuffer.get(512);
        System.out.println(b);
    }

}
