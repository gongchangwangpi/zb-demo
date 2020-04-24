package com.test.format;

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

        System.out.println(calcRate(1, 8));
        System.out.println(calcRate(2, 8));
        System.out.println(calcRate(3, 8));
        System.out.println(calcRate(5, 8));
        System.out.println(calcRate(6, 8));

    }

    private static BigDecimal calcRate(Integer stayCount, Integer totalCount) {
        return new BigDecimal(stayCount).divide(new BigDecimal(totalCount), 2, RoundingMode.HALF_DOWN);
    }
}
