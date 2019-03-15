package com.test;

/**
 * @author zhangbo
 */
public class MaxLengthSubStringTest {
    
    /**
     * 在一个以空格分隔的字符串中，
     * 求最长子串长度，时间和空间复杂度尽可能最优。
     * 例如："a bc defg ghi j k l mn op q  lkadfgd kldsg kjsa jgds lkdjg kljsdkl fjkl sdfkljsl kdjflkjdg lkasjglkj slkdgjslk adjgl ksadjf  klsdlkfs lkdjfsl adgklfjg"
     */
    public static void main(String[] args) {
        
        String s = "1234567890123456 1234567890123456 defg ghi j k l mn op q  lkadfgd kldsg kjsa jgds lkdjg kljsdkl fjkl sdfkljsl kdjflkjdg lkasjglkj slkdgjslk adjgl ksadjf  klsdlkfs lkdjfsl ";

        int length = s.length();
        int max = 0;
        int start = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (' ' == c) {
                max = max > start ? max : start;
                start = 0;
            } else if (i == length - 1) {
                max = max > start + 1 ? max : start + 1;
            } else {
                start++;
            }
        }

        System.out.println(max);

        int max2 = 0;
        String[] split = s.split(" ");
        for (String str : split) {
            int length1 = str.length();
            max2 = length1 > max2 ? length1 : max2;
        }
        System.out.println(max2);
    }
    
}
