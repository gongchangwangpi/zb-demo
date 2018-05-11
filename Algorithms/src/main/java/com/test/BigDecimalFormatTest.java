package com.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhangbo
 */
public class BigDecimalFormatTest {

    public static void main(String[] args) {

        BigDecimal b1 = new BigDecimal(20000.365);
        BigDecimal b2 = b1.setScale(2, RoundingMode.HALF_DOWN);
        System.out.println(b2);
    }
    
}
