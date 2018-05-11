package com.books.jvm.d3_6;

/**
 * 新生代对象晋升到老年代的年龄阈值
 * 分别设置阈值为1和15，查看GC日志
 *
 *  -XX:+UseSerialGC
 *  -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M
 *  -XX:MaxTenuringThreshold=1
 *  
 * 当阈值为1时，可用看到在第二次GC时，5201K->0K(9216K)新生代占用空间为0，
 * 如果survivor中相同年龄的对象大小总和大于survivor空间的一半，
 * 年龄大于或等于该年龄的对象可用直接进入老年代，无需等到MaxTenuringThreshold规定的年龄
 * 
 * 
 * Created by Administrator on 2017/3/25 0025.
 */
public class MaxTenuringThresholdTest {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {

        byte[] allocation1 = new byte[2 * _1M];
        byte[] allocation2 = new byte[2 * _1M];
        byte[] allocation3 = new byte[4 * _1M];
        byte[] allocation4 = new byte[4 * _1M];
    }

}
