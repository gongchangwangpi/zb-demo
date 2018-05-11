package com.books.jvm.d8;

/**
 * overload多参数重载
 *
 *Error:(17, 13) java: 对test的引用不明确
 *   com.books.jvm.d8.OverloadMultiArgsTest 中的方法 test(int,int)
 *   和 com.books.jvm.d8.OverloadMultiArgsTest 中的方法 test(short,long) 都匹配
 *
 * Created by Administrator on 2017/5/8 0008.
 */
public class OverloadMultiArgsTest {

    public static void main(String[] args) {
        OverloadMultiArgsTest test = new OverloadMultiArgsTest();
        byte b = 1;
        short s = 2;
        int i = 3;
        test.test(s, i);
    }
    public void test(byte b, int i) {
        System.out.println("byte...int");
    }
//    public void test(short s, int i) {
//        System.out.println("short...int");
//    }
    public void test(int i1, int i2) {
        System.out.println("int...int");
    }
//    public void test(short s, long l) {
//        System.out.println("short...long");
//    }
    public void test(int i, long l) {
        System.out.println("int...long");
    }
}
