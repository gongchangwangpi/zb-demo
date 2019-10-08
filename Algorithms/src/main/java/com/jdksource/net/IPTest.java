package com.jdksource.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;

/**
 * @author zhangbo
 * @date 2019-09-09
 */
public class IPTest {

    public static void main(String[] args) throws Exception {

        InetAddress ip = InetAddress.getLocalHost();

        System.out.println(ip.getHostAddress());

        System.out.println(Arrays.toString(ip.getAddress()));

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        System.out.println(network);

        byte[] mac = network.getHardwareAddress();

        System.out.println(Arrays.toString(mac));


    }

}
