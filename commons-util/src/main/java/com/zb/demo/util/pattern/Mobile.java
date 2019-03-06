package com.zb.demo.util.pattern;

import java.util.regex.Pattern;

/**
 * @author zhangbo
 */
public class Mobile {
    
    public static final String MOBILE = "^1[0-9]{10}";
    public static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE);

    public static boolean isMobile(String mobile) {
        return MOBILE_PATTERN.matcher(mobile).matches();
    }
    
    public static void main(String[] args) {
        
        String m = "13000010002";
        System.out.println(isMobile(m));
        
        m = "1300001000";
        System.out.println(isMobile(m));
        
        m = "13000010002";
        System.out.println(isMobile(m));
        
        m = "1300001000.";
        System.out.println(isMobile(m));
        
    }
    
}
