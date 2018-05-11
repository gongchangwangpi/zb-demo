package com.books.cysfsc.d6;

import java.time.Year;

/**
 * 闰年
 * 四年一闰，百年不闰，但四百年再闰
 * 
 * @author zhangbo
 */
public class LeapYear {

    public static void main(String[] args) {

        System.out.println(isLeapYear(2000));
        System.out.println(isLeapYear(2012));

        System.out.println(Year.isLeap(2000));
        System.out.println(Year.isLeap(2012));
    }
    
    private static boolean isLeapYear(int year) {
        boolean r4 = year % 4 == 0;
        boolean r100 = year % 100 == 0;
        boolean r400 = year % 400 == 0;
        return r400 || (r4 && !r100);
    }
}
