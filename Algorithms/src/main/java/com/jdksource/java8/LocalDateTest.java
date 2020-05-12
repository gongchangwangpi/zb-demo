package com.jdksource.java8;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zhangbo
 */
public class LocalDateTest {

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();
        System.out.println(today);

        LocalDate yesterday = today.minusDays(1);
        System.out.println(yesterday);

        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = today.atStartOfDay(zoneId).toInstant();
        System.out.println(today.toEpochDay());
        System.out.println(today.toEpochDay()/365);

        Date date = Date.from(instant);
        System.out.println(date);

    }
    
}
