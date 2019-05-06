package com.zb.demo.util.io;

import java.util.Scanner;

/**
 * @author zhangbo
 */
public class ConsoleUtil {
    
    private static volatile boolean autoClose = false;

    private ConsoleUtil() {
    }
    
    public static String read() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                return scanner.next();
            }
            return null;
        } finally {
            if (autoClose && scanner != null) {
                scanner.close();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println(read());
        }
    }
}
