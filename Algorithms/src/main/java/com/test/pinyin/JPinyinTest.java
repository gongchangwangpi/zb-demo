package com.test.pinyin;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author zhangbo
 */
public class JPinyinTest {

    public static void main(String[] args) throws Exception {

        // 首字母 nh
        System.out.println(PinyinHelper.getShortPinyin("您好"));
        // nín hǎo
        System.out.println(PinyinHelper.convertToPinyinString("您好", " ", PinyinFormat.WITH_TONE_MARK));
        // nin2 hao3
        System.out.println(PinyinHelper.convertToPinyinString("您好", " ", PinyinFormat.WITH_TONE_NUMBER));
        // nin hao
        System.out.println(PinyinHelper.convertToPinyinString("您好", " ", PinyinFormat.WITHOUT_TONE));

    }
    
}
