package com.jdksource.time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * @author zhangbo
 * @date 2019-11-11
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000, 0, OffsetDateTime.now().getOffset());

        System.out.println(localDateTime);


    }

}
