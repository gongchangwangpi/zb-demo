package com.books.jvm.d8;

/**
 * 对参数null的重载测试
 * 首先，null不能转换为基本类型，也不属于明确的任一类型，只能转换为引用类型。
 * 所以针对String.valueOf()方法，会有 {@link String#valueOf(char[])} 和 {@link String#valueOf(Object)} 两个方法符合null。
 * 然后，由于 char[] 可以认为是Object类型，但Object不能认为是 char[] ，
 * 所以会调用{@link String#valueOf(char[])}这个重载方法。
 *
 * @author zhangbo
 * @date 2020/4/2
 */
public class OverloadNullTest {

    public static void main(String[] args) {
//        t(null); // Ambiguous method call. Both t(char[]) and t(int[]) in OverloadNullTest match
        System.out.println(String.valueOf(null)); // NPE,实际调用为 char[]的重载方法
    }

    private static void t(char c) {
        System.out.println("char");
    }

    private static void t(char[] c) {
        System.out.println("char[]");
    }

    private static void t(int[] c) {
        System.out.println("int[]");
    }

    private static void t(Object c) {
        System.out.println("object");
    }

    private static void t(Object[] c) {
        System.out.println("object[]");
    }


}
