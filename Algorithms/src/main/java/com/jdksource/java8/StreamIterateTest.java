package com.jdksource.java8;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author zhangbo
 */
public class StreamIterateTest {

    public static void main(String[] args) {

        Stream.iterate(1, i -> i + 2)
                .limit(100)
                .forEach(System.out::println);
        
        Stream.iterate(new Date(), (Date date) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return calendar.getTime();
        })
                .limit(40)
                .forEach(System.out::println);

        
        
    }
    
}
