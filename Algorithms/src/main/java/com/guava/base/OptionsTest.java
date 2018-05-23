package com.guava.base;

import com.google.common.base.Optional;

/**
 * @author zhangbo
 */
public class OptionsTest {

    public static void main(String[] args) {

        String s = null;
        
        Optional<String> optional = Optional.of(s);

        boolean present = optional.isPresent();

        System.out.println(present);


    }
    
}
