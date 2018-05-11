package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 插入排序法
 * 先将数组的前2个数按从小到大的顺序排好，
 * 然后比较第三个数，将它插入前面已经排好序的数中，以此类推
 * 
 * 适用于数据已经有一定的顺序，此时效率较高，移动数据较少
 * 
 * @author zhangbo
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {123,58,89,264,5,45};
        System.out.println("排序之前排序结果为：" + Arrays.toString(arr));

        insertionSort(arr);

    }

    private static void insertionSort(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length; i++) {
            temp = arr[i];
            
            /*int j;
            for (j = i - 1; j >=0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
            }*/
            int j = i - 1;
            while (j >=0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
            
            System.out.println("第 " + i + " 次排序结果为：" + Arrays.toString(arr));
        }
    }
    
}
