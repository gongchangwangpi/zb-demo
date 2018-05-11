package com.books.cysfsc.d5;

/**
 * 折半查找/二分查找
 * 
 * @author zhangbo
 */
public class BinarySearch {

    public static void main(String[] args) {
        
        int[] a = {3,5,8,12,56,99,123,687,886};
        
        int b = 99;
        
        int index = binarySearch(a, b);

        System.out.println(index);
        
    }

    private static int binarySearch(int[] a, int b) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            // 重新计算mid
            int mid = (high + low) / 2;
            if (b == a[mid]) {
                return mid;
            } else if (b > a[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

}
