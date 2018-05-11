package com.books.suanfa4.d1._1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class Lx1_1_181 {

    public static void main(String[] args) {
//        long startA = System.nanoTime();
//        int a = a(50);
//        long endA = System.nanoTime();
//        System.out.println(a);
//        System.out.println(endA - startA);


        long startB = System.nanoTime();
        int b = b(100);
        long endB = System.nanoTime();
        System.out.println(b);
        System.out.println(endB - startB);

        long startC = System.nanoTime();
        int c = c(100);
        long endC = System.nanoTime();
        System.out.println(c);
        System.out.println(endC - startC);


    }

    public static int a(int i) {
        if (i <= 0) return 0;
        if (i == 1) return 1;
        if (i == 2) return 2;
        return a(i - 2) + a(i - 1);
    }


    /**
     * 分阶段分析
     *
     * 备忘录算法  将相同参数相同结果的保存起来
     *
     * 占用时间和空间
     *
     * @param i
     * @return
     */
    public static Map<Integer, Integer> bMap;
    public static int b(int i) {
        if (bMap == null) {
            bMap = new HashMap<>();
            bMap.put(1, 1);
            bMap.put(2, 2);
        }
        if (bMap.containsKey(i)) {
            return bMap.get(i);
        }
        if (i <= 0) return 0;
        if (i == 1){
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        for (int j = 3; j <= i; j++) {
            bMap.put(j, bMap.get(j-1) + bMap.get(j-2));
        }
        return bMap.get(i);
    }

    /**
     * 动态规划，每次只保存前两次的结果，节省时间和空间
     * Map的查询时间和储存全部参数结果的空间
     *
     * @param i
     * @return
     */
    public static int c(int i) {
        if (i <= 0) return 0;
        if (i == 1){
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        int a = 1;
        int b = 2;
        int tmp = 0;
        for (int j = 3; j <= i; j++) {
            tmp = a + b;
            a = b;
            b = tmp;
        }
        return tmp;
    }
}
