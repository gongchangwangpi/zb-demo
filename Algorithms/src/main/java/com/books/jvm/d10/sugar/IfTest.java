package com.books.jvm.d10.sugar;

/**
 * 条件语句
 * 这是编译器解除语法糖阶段完成（com.sum.tools.javac.comp.Lower.java）
 *
 *
 * Created by Administrator on 2017/5/18 0018.
 */
public class IfTest {
    // class反编译后的代码，如果if条件为常量true，则会编译优化
    // public static void main(String[] args) {
    //     System.out.println(true);
    // }
    public static void main(String[] args) {

        if (true) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

}
