package com.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangbo
 */
public class IpConverter {
    
    public static final String IP_SPLIT = ".";
    public static final long MAX_IP_V4 = (2L << 31) - 1;
    
    public static final long MASK = 255;
    

    public static void main(String[] args) {
        System.out.println(convertIpv4("172.18.8.168"));
        System.out.println(convertIpv4(2886863016L));
        
        System.out.println(convertIpv4("255.255.255.256"));
        System.out.println(convertIpv4(4294967296L));
    }
    
    public static String convertIpv4(long ipv4) {
        if (ipv4 > MAX_IP_V4) {
            throw new IllegalArgumentException("ipv4 不正确：" + ipv4);
        }
        return (ipv4 >> 24 & MASK) + IP_SPLIT + 
                (ipv4 >> 16 & MASK) + IP_SPLIT + 
                (ipv4 >> 8 & MASK) + IP_SPLIT + 
                (ipv4 & MASK);
    }

    public static long convertIpv4(String ipv4) {
        if (StringUtils.isEmpty(ipv4)) {
            throw new IllegalArgumentException("ipv4 is empty");
        }
        String[] arr = ipv4.split("\\.");
        return parseIpv4Number(arr[0]) << 24 |
                parseIpv4Number(arr[1]) << 16 |
                parseIpv4Number(arr[2]) << 8 |
                parseIpv4Number(arr[3]);
    }

    private static long parseIpv4Number(String s) {
        try {
            long num = Long.parseLong(s);
            if (num < 0 || num > MASK) {
                throw new IllegalArgumentException("ipv4 不正确");
            }
            return num;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ipv4 不正确");
        }
    }

}
