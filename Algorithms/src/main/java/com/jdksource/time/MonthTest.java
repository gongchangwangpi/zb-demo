package com.jdksource.time;

import java.time.Month;
import java.util.Arrays;

/**
 * @author zhangbo
 */
public class MonthTest {

    public static void main(String[] args) {

//        Month month = Month.of(15);
//        System.out.println(month); // 异常

        Month month1 = Month.of(12);
        System.out.println(month1); // DECEMBER

        Month[] months = Month.values();
        // [JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER]
        System.out.println(Arrays.toString(months));

    }
    
}
