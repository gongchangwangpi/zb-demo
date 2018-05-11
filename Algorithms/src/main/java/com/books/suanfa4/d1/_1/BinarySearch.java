package com.books.suanfa4.d1._1;

import com.bedpotato.alg4.utils.StdIn;
import com.bedpotato.alg4.utils.StdOut;

import java.util.Arrays;

/**
 * 二分查找法
 * 针对已排好序的数组
 *
 * Created by Administrator on 2017/6/12 0012.
 */
public class BinarySearch {

    public static void main(String[] args) {
        /*int[] arr = {1, 3, 4, 6, 8, 9, 12, 154,555, 678, 7899};
//        int[] arr = {999, 789, 567, 345, 123, 67, 34, 22, 19, 12, 3};
        long start = System.nanoTime();
        int rank = rank(3, arr);
        long end = System.nanoTime();

        int rank1 = rank1(3, arr);

        long end1 = System.nanoTime();

        System.out.println(rank + " --  " + (end - start));
        System.out.println(rank1 + " --  " + (end1 - end));*/

        int[] ints = StdIn.readAllInts();
        Arrays.sort(ints);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, ints) < 0) {
                StdOut.println(key);
            }
        }

    }

    public static int rank(int key, int[] arr) {
        int low = 0;
        int hi = arr.length - 1;
        while (low <= hi) {
            int mid = low + (hi - low) / 2;
            if (key < arr[mid]) {
                hi = mid - 1;
            } else if (key > arr[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 递归实现二分查找
     * @param key
     * @param arr
     * @return
     */
    public static int rank1(int key, int[] arr) {
        return rank(key, arr, 0, arr.length - 1);
    }

    /**
     * 递归实现二分查找
     *
     * @param key
     * @param arr
     * @param lo
     * @param hi
     * @return
     */
    private static int rank(int key, int[] arr, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < arr[mid]){
            return rank(key, arr, lo, mid - 1);
        } else if (key > arr[mid]) {
            return rank(key, arr, mid + 1, hi);
        } else {
            return mid;
        }
    }
}
