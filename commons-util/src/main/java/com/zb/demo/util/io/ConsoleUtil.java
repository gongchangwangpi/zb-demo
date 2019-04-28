package com.zb.demo.util.io;

import java.util.Scanner;

/**
 * @author zhangbo
 */
public class ConsoleUtil {

    private ConsoleUtil() {
    }
    
    public static String read() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                return scanner.next();
            }
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(read());
    }
}
