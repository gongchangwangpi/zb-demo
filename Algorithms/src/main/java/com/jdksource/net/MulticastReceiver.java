package com.jdksource.net;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author zhangbo
 */
@Slf4j
public class MulticastReceiver {

    public static void main(String[] args) throws Exception {

        // 组播IP地址
        InetAddress inetAddress = InetAddress.getByName("239.0.0.5");
        int port = 19999;

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(inetAddress);

        int i = 0;
        
        Thread currentThread = Thread.currentThread();
        while (!currentThread.isInterrupted()) {
            
            byte[] bytes = new byte[1024];

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, port);
            // 发送
            multicastSocket.receive(datagramPacket);
            
            log.info("【MulticastReceiver】receive: {}", new String(bytes, "UTF-8"));   
        }

        multicastSocket.close();
    }
    
}
