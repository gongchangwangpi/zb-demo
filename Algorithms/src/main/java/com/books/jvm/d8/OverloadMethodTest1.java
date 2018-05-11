package com.books.jvm.d8;

/**
 * 基本类型 overload 重载方法调用
 *
 * 变长参数, 包装类型的变长(包含Object...) 和 基本类型的变长 不能同时存在
 *
 *      如同时存在会报错
 *           : 对test的引用不明确com.books.jvm.d8.OverLoadMethodTest1 中的方法 test(double...) 和 com.books.jvm.d8.OverLoadMethodTest1 中的方法 test(java.lang.Byte...) 都匹配
 *
 *      基本类型的变长也会按照精度提升的方式依次执行
 *      包装类型的变长只会执行参数本身的包装类及Object变长
 *
 * byte -- short -- int -- long -- float -- double -- Byte -- Number/Comparable -- Object --
 *      -- 基本类型变长(按照精度提升) / 包装类型变长 -- Object变长 --
 *           // 如同时存在Number 和 Comparable的参数,则编译报错Ambiguous method call
 *
 * char -- int -- long -- float -- double -- Character -- Comparable -- Object -- 变长
 *
 * Byte -- Number/Comparable -- Object -- byte -- short -- int -- long -- float -- double -- 变长
 *
 * Character -- Comparable -- Object -- char -- int -- long -- float -- double -- 变长
 *
 * Created by books on 2017/5/5.
 */
public class OverloadMethodTest1 {

    public static void main(String[] args) {
        OverloadMethodTest1 test1 = new OverloadMethodTest1();

        byte b = 2;
        test1.test(b);

//        char c = 'a';
//        test1.test(c);

//        Byte b = 2;
//        test1.test(b);

//        Character c = 'a';
//        Character c = new Character('a');
//        test1.test(c);

    }
    // ******** 基本类型 *******************
//    public void test(byte b) {
//        System.out.println("byte");
//    }
    public void test(short b) {
        System.out.println("short");
    }
    public void test(int b) {
        System.out.println("int");
    }
    public void test(long b) {
        System.out.println("long");
    }
    public void test(float b) {
        System.out.println("float");
    }
    public void test(double b) {
        System.out.println("double");
    }
    public void test(char b) {
        System.out.println("char");
    }
    // ******** 引用类型 ****************
    public void test(Byte b) {
        System.out.println("Byte");
    }
    public void test(Short b) {
        System.out.println("Short");
    }
    public void test(Integer b) {
        System.out.println("Integer");
    }
    public void test(Long b) {
        System.out.println("Long");
    }
    public void test(Float b) {
        System.out.println("Float");
    }
    public void test(Double b) {
        System.out.println("Double");
    }
    public void test(Character b) {
        System.out.println("Character");
    }
    public void test(Number b) {
        System.out.println("Number");
    }
    public void test(Comparable b) {
        System.out.println("Comparable");
    }
    public void test(String b) {
        System.out.println("String");
    }
    public void test(Object b) {
        System.out.println("Object");
    }
    // ********* 基本类型 变长参数 *********************
    public void test(byte... b) {
        System.out.println("byte...");
    }
    public void test(short... b) {
        System.out.println("short...");
    }
    public void test(int... b) {
        System.out.println("int...");
    }
    public void test(long... b) {
        System.out.println("long...");
    }
    public void test(float... b) {
        System.out.println("float...");
    }
    public void test(double... b) {
        System.out.println("double...");
    }
    public void test(char... b) {
        System.out.println("char...");
    }
    // ******** 引用类型 变长参数 ****************
    public void test(Byte... b) {
        System.out.println("Byte...");
    }
    public void test(Short... b) {
        System.out.println("Short...");
    }
    public void test(Integer... b) {
        System.out.println("Integer...");
    }
    public void test(Long... b) {
        System.out.println("Long...");
    }
    public void test(Float... b) {
        System.out.println("Float...");
    }
    public void test(Double... b) {
        System.out.println("Double...");
    }
    public void test(Character... b) {
        System.out.println("Character...");
    }
    public void test(String... b) {
        System.out.println("String...");
    }
    public void test(Object... b) {
        System.out.println("Object...");
    }
}
