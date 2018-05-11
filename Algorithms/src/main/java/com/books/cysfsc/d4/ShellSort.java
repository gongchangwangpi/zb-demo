package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 希尔排序法
 * 1.  先将数组(长度为n)分成 n/2组元素，第1个元素和第n/2 + 1个元素一组，2 -- n/2 + 2为一组
 *     以此类推进行，先将每组进行排序
 * 2.  在将数组分成 n/4组元素，再次排序
 * 3.  不断重复，直到最后剩下一个序列，再次排序
 * 
 * 最后一次使用插入排序，因前面几次排序已有一定的顺序，所以效率较高
 * 
 * @author zhangbo
 */
public class ShellSort {
    
    public static void main(String[] args) {
        
        int[] arr = {123,58,89,264,5,45,3,99};
        
        System.out.println("排序之前排序结果为：" + Arrays.toString(arr));

        shellSort(arr);

    }

    private static void shellSort(int[] arr) {
        int temp;
        int k = 0;
        for (int r = arr.length / 2; r >= 1; r /= 2) { // 分组
            for (int i = r; i < arr.length; i++) { // 比较分组类对应的元素
                temp = arr[i];
                int j = i - r;
                while (j >= 0 && temp < arr[j]) { // 插入排序
                    arr[j + r] = arr[j];
                    j -= r;
                }
                arr[j + r] = temp;
            }
            System.out.println("第 " + k + " 次排序结果为：" + Arrays.toString(arr));
            k++;
        }
    }
}
