package com.zb.springboot.demo.util;

import com.alibaba.testable.core.annotation.MockMethod;
import org.junit.jupiter.api.Test;

/**
 * @author bo6.zhang
 * @date 2021/3/16
 */
public class StringUtilTest {


    @Test
    public void isEmptyTest() {
        String s = "";
        StringUtil.isEmpty(s);
    }

    public static class Mock {

        @MockMethod(targetClass = StringUtil.class)
        private static boolean isNull(String s) {
            System.out.println("===== mock isNull");
            return true;
        }
    }

}
