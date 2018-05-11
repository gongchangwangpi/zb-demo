package com.books.jvm.d2_4;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池OutOfMemoryException
 * jdk 1.6 会出现OOM
 * 角度看1.7 1.8 不会
 *
 * Created by Administrator on 2017/3/21 0021.
 */
public class RuntimeConstantPoolOOM {

    private static final String s4 = "我是常亮";

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();

        int i = 0;
       /* while (true) {
            System.out.println(i);
            list.add(String.valueOf(i++).intern());
        }*/

//        String s1 = new StringBuilder(("计算机")).append("软件").toString();
//
        String s3 = "java";
        String s2 = new StringBuilder("ja").append("va").toString();
//
//        System.out.println(s1.intern() == s1);
        String s22 = s2.intern();
        System.out.println(s22 == s2);
//        System.out.println(System.identityHashCode(s2));
//        System.out.println(System.identityHashCode(s22));

        String s33 = s3.intern();
        System.out.println(s3 == s33);
//        System.out.println(System.identityHashCode(s3));
//        System.out.println(System.identityHashCode(s33));

        System.out.println(s4 == s4.intern());

        String s5 = "aa" + "bb";
        System.out.println(s5 == "aabb");


    }

}
