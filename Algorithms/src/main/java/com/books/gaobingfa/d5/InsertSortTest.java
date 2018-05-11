package com.books.gaobingfa.d5;

import java.util.Arrays;

/**
 * 插入排序
 *
 * Created by books on 2017/5/2.
 */
public class InsertSortTest {

    public static void main(String[] args) {
        int[] arr = {123,55,130,0,5,9,4,1,999,456,11};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 假设前面一部分已经排好序，依次遍历后面的元素，
     * 和前面部分的元素比较，插入到前面部分合适的位置，
     * 直到未排序的元素为0
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int length = arr.length;
        int i, j, key;// key为要插入的元素

        for (i = 1; i < length; i++) {
            // 准备插入的元素
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            // 找到合适的位置
            arr[j + 1] = key;
        }

    }
}
