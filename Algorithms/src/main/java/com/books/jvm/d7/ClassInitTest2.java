package com.books.jvm.d7;

/**
 * 创建该类型的数组，也只会触发加载，不会初始化
 *
 * Created by Administrator on 2017/4/27 0027.
 */
public class ClassInitTest2 {

    public static void main(String[] args) {
        Arr[] arrs = new Arr[5];
        System.out.println(arrs);
    }

}

class Arr {
    static {
        System.out.println("arr init");
    }
}
