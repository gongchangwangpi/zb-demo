package com.test;

/**
 * Created by books on 2017/4/27.
 */
public class ArrayCopyTest {

    public static void main(String[] args) {

        Object[] objArr = new Object[5];
        objArr[0] = new Object();
        System.out.println(objArr);
        System.out.println(objArr.getClass());

        int[] intArr = new int[]{1, 2, 3};
        System.out.println(intArr.getClass());

        Integer[] integerArr = new Integer[]{1, 2, 3};
        System.out.println(integerArr.getClass());

        Integer[] integerClone = integerArr.clone();
        System.out.println(integerClone);

        Object[] objClone = objArr.clone();
        System.out.println(objClone);
        System.out.println(objArr[0]);
        System.out.println(objClone[0]);
    }

}
