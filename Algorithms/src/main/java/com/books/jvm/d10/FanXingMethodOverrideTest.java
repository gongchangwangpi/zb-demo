package com.books.jvm.d10;

/**
 * 泛型参数 方法重载
 *
 * Created by Administrator on 2017/5/17 0017.
 */
public class FanXingMethodOverrideTest {

    // 编译不通过，test(List<String> list) clashes with test(List<Integer> list); both methods have same erasure
    /*public void test(List<String> list) {
        System.out.println("List<String> test");
    }

    public void test(List<Integer> list) {
        System.out.println("List<Integer> test");
    }*/

}
