package com.books.jvm.d10;

/**
 * 泛型参数 方法重载
 * 使用Sun JDK 1.6 可以编译通过，针对返回值类型不同的可以编译通过
 *
 *
 * Created by Administrator on 2017/5/17 0017.
 */
public class FanXingMethodOverrideTest1 {

    // 编译不通过，test(List<String> list) clashes with test(List<Integer> list); both methods have same erasure
    /*public String test(List<String> list) {
        System.out.println("List<String> test");
        return "string";
    }

    public int test(List<Integer> list) {
        System.out.println("List<Integer> test");
        return 1;
    }*/

}
