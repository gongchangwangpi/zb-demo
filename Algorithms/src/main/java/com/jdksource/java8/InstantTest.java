package com.jdksource.java8;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * @author zhangbo
 */
public class InstantTest {

    public static void main(String[] args) {
        
        Instant now1 = Instant.now();
        Instant now2 = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(8)));

        System.out.println(now1);
        System.out.println(now2);
    }
    
}
