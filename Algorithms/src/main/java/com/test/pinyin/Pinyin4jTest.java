package com.test.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author zhangbo
 */
public class Pinyin4jTest {

    public static void main(String[] args) throws Exception {
        
        String china = "您好,去哪儿";

        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        // 大小写
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 声调方式
        outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        // 元音ü 的显示 v / ü / u:
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        String s = PinyinHelper.toHanYuPinyinString(china, outputFormat, " ", false);

        System.out.println(s);
        
    }
}
