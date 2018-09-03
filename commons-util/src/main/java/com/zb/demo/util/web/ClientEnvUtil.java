package com.zb.demo.util.web;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端环境工具类
 * 
 * @author zhangbo
 */
public final class ClientEnvUtil {
    
    private static final String WECHAT = "MicroMessenger";
    private static final String IOS = "iPhone";
    private static final String IPAD = "iPad";
    private static final String ANDROID = "Android";
    private static final String WINDOWS = "Windows";
    
    
    public static String currentEnv(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (StringUtils.isEmpty(userAgent)) {
            return "userAgent is empty";
        }
        
        return "other";
    }
    
    public static boolean isWechat(HttpServletRequest request) {
        return false;
    }
    
    public static boolean isMobile(HttpServletRequest request) {
        return false;
    }
    
    public static boolean isPc(HttpServletRequest request) {
        return false;
    }
}
