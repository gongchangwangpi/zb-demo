package com.jdksource.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author zhangbo
 * @date 2020/8/5
 */
public class InetAddressTest {

    public static void main(String[] args) throws UnknownHostException {

        InetAddress[] inetAddresses = InetAddress.getAllByName("www.baidu.com");

        for (InetAddress inetAddress : inetAddresses) {
            // www.baidu.com/220.181.38.150
            System.out.println(inetAddress.toString());
            // www.baidu.com
            System.out.println(inetAddress.getHostName());
            // IP地址 [-36, -75, 38, -106]
            System.out.println(Arrays.toString(inetAddress.getAddress()));
            // 220.181.38.150
            System.out.println(inetAddress.getHostAddress());
        }

    }

}
