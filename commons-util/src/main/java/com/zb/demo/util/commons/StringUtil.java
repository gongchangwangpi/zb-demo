package com.zb.demo.util.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * 中文正则
     */
    private static String REGEX_CHINESE_SUFFIX = "[\u4e00-\u9fa5]+$";
    /**
     * 中文正则
     */
    private static String REGEX_CHINESE_PREFIX = "^[\u4e00-\u9fa5]+";

    /**
     * 去除两端的中文
     * 
     * @param str
     * @return
     */
    public static String removeChineseBothEnds(String str) {
        // 去除前面中文
        Pattern patternPre = Pattern.compile(REGEX_CHINESE_PREFIX);
        Matcher matcherPre = patternPre.matcher(str);
        str = matcherPre.replaceAll("");
        if (log.isDebugEnabled()) {
            log.debug("removed chinese prefix: {}", str);
        }

        // 去除后面中文
        Pattern patternSuf = Pattern.compile(REGEX_CHINESE_SUFFIX);
        Matcher matcherSuf = patternSuf.matcher(str);
        str = matcherSuf.replaceAll("");
        if (log.isDebugEnabled()) {
            log.debug("removed chinese suffix: {}", str);
        }
        return str;
    }
}
