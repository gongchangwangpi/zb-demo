package com.test.pinyin;

import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author zhangbo
 */
public class JPinyinTest {

    public static void main(String[] args) throws Exception {

        String shortPinyin = PinyinHelper.getShortPinyin("中华人民共和国");
        System.out.println(shortPinyin);

    }
    
}
