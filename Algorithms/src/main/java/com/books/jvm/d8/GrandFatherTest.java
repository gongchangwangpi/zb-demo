package com.books.jvm.d8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Page 268 实际结果与书上不符
 *
 * 调用父类的父类中的方法
 *
 * Created by Administrator on 2017/5/10 0010.
 */
public class GrandFatherTest {

    public static void main(String[] args) {
        (new GrandFatherTest().new Son()).say();
    }

    class GrandFather {
        void say() {
            System.out.println("GrandFather");
        }
    }

    class Father extends GrandFather {
        void say() {
            System.out.println("Father");
        }
    }

    class Son extends Father {
        void say() {
            // 在此处要实现调用GrandFather的say();
            // super.say();
            try {
                MethodType methodType = MethodType.methodType(void.class);
                MethodHandle say = lookup().findSpecial(GrandFather.class, "say", methodType, getClass());
                say.invoke(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

}
