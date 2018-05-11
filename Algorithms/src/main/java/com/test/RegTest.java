package com.test;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * Created by books on 2017/5/22.
 */
public class RegTest {

    private static void validateUserPin(String userPin) {
        String errorTip = "用户名只能为小写英文字母和数字,且以英文字母开头";

        if (StringUtils.isEmpty(userPin)) {
            throw new IllegalArgumentException(errorTip);
        }

        Pattern pattern = Pattern.compile("^[a-z][a-z0-9]{3,31}$");
        Matcher matcher = pattern.matcher(userPin);

        if(!matcher.matches()){
            throw new IllegalArgumentException(errorTip);
        }
    }

    public static void main(String[] args) {
        String userPin = "q12";
        validateUserPin(userPin);
    }


}
