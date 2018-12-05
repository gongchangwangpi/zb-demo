package com.jdksource.java8;

import java.util.Optional;

/**
 * @author zhangbo
 */
public class OptionalNullPointerTest {

    public static void main(String[] args) {

        Optional optional = null;

        System.out.println(optional.isPresent());
        
    }
    
}
