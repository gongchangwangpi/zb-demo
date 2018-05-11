package com.books.gaobingfa.d5;

import java.util.Arrays;

/**
 * 奇偶排序
 * <p>
 * Created by books on 2017/5/2.
 */
public class OddEvenSortTest {

    public static void main(String[] args) {

        int[] arr = {2,8,4,1233,45,9,456,1,33,7};
        oddEvenSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 先从偶数索引开始比较交换，如果有交换或者下次是奇数则进入下次循环；
     * 若奇数索引也无需交换，则表示排序完成
     * 若奇数索引有交换，则进入下次偶数索引比较交换，
     * 最终保证偶数/奇数是成对完成的，表示偶数/奇数都无需再交换，则排序完成
     *
     * @param arr
     */
    public static void oddEvenSort(int[] arr) {
        int exchFlag = 1, start = 0;
        while (exchFlag == 1 || start == 1) {
            exchFlag = 0;

            for (int i = start; i < arr.length - 1; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    exchFlag = 1;
                }
            }
            if (start == 0)
                start = 1;
            else
                start = 0;
        }
    }
}
