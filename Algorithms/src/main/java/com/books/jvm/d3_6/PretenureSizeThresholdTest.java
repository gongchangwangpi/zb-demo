package com.books.jvm.d3_6;

/**
 * 大对象直接在老年代分配内存
 *
 * -XX:+UseSerialGC -XX:PretenureSizeThreshold=3145728
 * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M
 *
 * Created by Administrator on 2017/3/25 0025.
 */
public class PretenureSizeThresholdTest {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {

        byte[] bytes = new byte[4 * _1M];
        // GC日志 直接在老年代分配内存
        // tenured generation total 10240K, used 4096K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)

    }

}
