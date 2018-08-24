package com.books.jvm_sz.chapter7;

/**
 * 
 * -Xms10M -Xmx10M -XX:PermSize10M -XX:MaxPermSize10M -XX:MaxMetaspaceSize=10M
 * 
 * @author zhangbo
 */
public class StringInternTest {

    public static void main(String[] args) {

        String s = "";
//        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < 100_000_000; i++) {
//            System.out.println(i);
            if (i <= 7168) {
                s += "123456789das6df5safd45a6r4ew513f2dsa31dds2a31dfsa31fd3as1fd3a1fd3as1fdsa";
            } else {
                s += "" + i;
            }
            String intern = s.intern();
//            list.add(intern);
        }

    }
    
}
