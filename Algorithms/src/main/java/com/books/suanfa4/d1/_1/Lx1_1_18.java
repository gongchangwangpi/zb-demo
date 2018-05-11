package com.books.suanfa4.d1._1;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class Lx1_1_18 {

    public static void main(String[] args) {

//        int mystery = mystery(2, 25);
        int mystery = mystery(3, 8);
        System.out.println(mystery);

        int mystery2 = mystery2(2, 12);
        System.out.println(mystery2);
    }

    public static int mystery(int a, int b) {
        if (b == 0) {
            return 0;
        }
        if (b % 2 == 0) {
            return mystery(a + a, b / 2);
        }
        return mystery(a + a, b / 2) + a;
    }
    public static int mystery2(int a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b % 2 == 0) {
            return mystery(a * a, b / 2);
        }
        return mystery(a * a, b / 2) + a;
    }
}
