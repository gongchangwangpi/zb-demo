package com.jdksource.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangbo
 */
public class YearTest {

    public static void main(String[] args) {

        Year year1 = Year.now();
        System.out.println(year1); // 2018

        Year year2 = Year.of(2017);
        System.out.println(year2); // 2017

        Year year3 = Year.parse("2016");
        System.out.println(year3); // 2016

        Year year4 = Year.parse("2018-02-03", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(year4); // 2018
        // 是否是闰年
        boolean leap = year3.isLeap();
        System.out.println(leap); // true

        boolean leap1 = year1.isLeap();
        System.out.println(leap1); // false

        System.out.println(year1.length()); // 365
        System.out.println(year3.length()); // 366
    }
    
}
