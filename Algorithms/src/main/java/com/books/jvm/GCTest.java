package com.books.jvm;

import java.io.IOException;

/**
 * jdk1.7/1.8 默认为Parallel Scavenge和Parallel Old
 * 
 * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M
 * 
 * jdk1.7/1.8 下使用 -XX:+UseConcMarkSweepGC，新生代默认搭配ParNew
 * 
 * 
 * @author zhangbo
 */
public class GCTest {

    private static final int _1M = 1024 * 1024;
    
    public static void main(String[] args) throws IOException {

        byte[] allocation1 = new byte[2 * _1M];
        byte[] allocation2 = new byte[2 * _1M];
        byte[] allocation3 = new byte[2 * _1M];
        byte[] allocation4 = new byte[4 * _1M];
        byte[] allocation5 = new byte[4 * _1M];
        byte[] allocation6 = new byte[2 * _1M];
        
        System.in.read();
    }
    
}
