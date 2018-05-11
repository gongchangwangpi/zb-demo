package com.books.jvm.d3_6;

/**
 * 对象内存分配 Page93, GC日志与书中有不同，书中是前面的6M在GC时进入老年代
 * 
 * 使用Serial垃圾收集器，堆内存固定为20M，
 * 其中Young区分10M，Old区10M，但Young区总可用空间为9M，
 * 因from/to/to默认为8:1:1
 * 如不加 -XX:+UseSerialGC，jdk1.8默认使用Parallel Scavenge收集器
 *
 *  -XX:+UseSerialGC
 *  -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M
 *
 * Created by Administrator on 2017/3/25 0025.
 */
public class AllocateMemoryTest {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {

        byte[] allocation1 = new byte[2 * _1M];
        byte[] allocation2 = new byte[2 * _1M];
        byte[] allocation3 = new byte[2 * _1M];
        byte[] allocation4 = new byte[4 * _1M]; // 最后4M由于担保分配，直接进入老年代
    }

}
