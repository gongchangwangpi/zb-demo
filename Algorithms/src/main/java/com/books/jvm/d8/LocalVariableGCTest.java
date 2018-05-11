package com.books.jvm.d8;

/**
 * -XX:+PrintGCDetails
 *
 * Created by Administrator on 2017/5/3 0003.
 */
public class LocalVariableGCTest {

    public static void main(String[] args) {
        // ----------------------------------------------
        //
//        byte[] bytes = new byte[1024 * 1024 * 20];
//        System.gc();
        // ----------------------------------------------
//        {
//            byte[] bytes = new byte[1024 * 1024 * 20];
//        }
//        System.gc();
        // -----------------------------------------------
        // 只有这个才会回收
        {
            byte[] bytes = new byte[1024 * 1024 * 20];
        }
        int i = 0;
        System.gc();
    }

}
