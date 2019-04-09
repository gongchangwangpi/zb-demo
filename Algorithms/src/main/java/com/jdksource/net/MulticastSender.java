package com.jdksource.net;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

/**
 * 在IP协议中，规定的D类IP地址为组播地址。
 * 224.0.0.0~239.255.255.255这个范围内的IP都是D类IP地址，其中有一些IP段是保留的有特殊含义的：
 *
 * 224.0.0.0～224.0.0.255：这个D类IP地址段为保留地址，不建议您在开发过程中使用，因为可能产生冲突。
 * 例如224.0.0.5这个组播地址专供OSPF协议（是一种路由策略协议，用于找到最优路径）使用的组播地址；
 * 224.0.0.18这个组播地址专供VRRP协议使用（VRRP协议是虚拟路由器冗余协议）；
 *
 * 224.0.1.0～224.0.1.255：这个D类IP地址为公用组播地址，用于在整个Internet网络上进行组播。
 * 除非您有顶级DNS的控制/改写权限，否则不建议在局域网内使用这个组播地址断；
 *
 * 239.0.0.0～239.255.255.255：这个D类IP地址段为推荐在局域网内使用的组播地址段。
 * 注意，如果要在局域网内使用组播功能，需要局域网中的交换机/路由器支持组播功能。
 * 幸运的是，目前市面上只要不是太过低端的交换机/路由器，
 * 都支持组播功能（组播功能所使用的主要协议为IGMP协议，关于IGMP协议的细节就不再进行深入了）
 * 
 * 
 * @author zhangbo
 */
@Slf4j
public class MulticastSender {

    public static void main(String[] args) throws Exception {

        // 组播IP地址
        InetAddress inetAddress = InetAddress.getByName("239.0.0.5");
        int port = 19999;

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(inetAddress);

        int i = 0;
        
        Thread currentThread = Thread.currentThread();
        while (!currentThread.isInterrupted()) {
            
            String message = "我是组播Sender: " + i++;
            byte[] bytes = message.getBytes("UTF-8");

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, port);
            // 发送
            multicastSocket.send(datagramPacket);
            log.info("【MulticastSender】send: {}", message);
            TimeUnit.SECONDS.sleep(1);
        }

        multicastSocket.close();
    }
    
}
