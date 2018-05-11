package com.books.jvm.d6;

import static java.lang.Float.NaN;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class Test {
    private int m;
    public int inc() {
        return m + 1;
    }
    protected Object obj = new Object();

    public synchronized void f() {

    }

    public int ff() {
        synchronized (obj) {
            System.out.println(11);
        }
        return 1;
    }

    public static void main(String[] args) {
        double d = 5.55;
        int i = (int) d;
        System.out.println(d);
        System.out.println(i);

        double d2 = NaN;
        System.out.println(d2);

        double d3 = 0;
        double d4 = 1.0;

        System.out.println(d3 / d3);
        System.out.println(d4 / d3);
    }
}
