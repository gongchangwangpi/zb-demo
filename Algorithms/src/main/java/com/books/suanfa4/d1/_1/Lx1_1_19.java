package com.books.suanfa4.d1._1;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class Lx1_1_19 {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + "  " + F(i));
        }
    }

    public static long F(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return F(n - 1) + F(n - 2);
    }
}
