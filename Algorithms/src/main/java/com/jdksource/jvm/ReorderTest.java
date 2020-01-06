package com.jdksource.jvm;

/**
 * 指令重排序
 *
 * @author zhangbo
 * @date 2019-12-21
 */
public class ReorderTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 0;

        while (true) {
            A a = new A();
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a.a = 1;
                    a.x = a.b;
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a.b = 1;
                    a.y = a.a;
                }
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            i++;

            if (a.x == 0 && a.y == 0) {
                System.out.println(i + "次 (a,b,x,y) = " + a.a + a.b + a.x + a.y);
                break;
            }

        }

    }

    private static class A {
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
    }
}
