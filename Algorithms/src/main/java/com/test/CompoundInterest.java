package com.test;

/**
 * 
 * 
 * @author zhangbo
 */
public class CompoundInterest {

    public static void main(String[] args) {

        System.out.println(month(1000, 0.01, 360));
        System.out.println(year(12000, 0.12, 30));
        
    }
    
    private static int month(int capital, double rate, int month) {
        int sum = 0;
        for (int i = 0; i < month; i++) {
            sum += capital * Math.pow((1 + rate), month - i);
        }
        return sum;
    }
    
    private static int year(int capital, double rate, int year) {
        int sum = 0;
        for (int i = 0; i < year; i++) {
            sum += capital * Math.pow((1 + rate), year - i);
        }
        return sum;
    }
    
}
