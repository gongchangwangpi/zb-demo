package com.jdksource.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * JMX Java Management Extension
 * 
 * 先启动本类的main方法，然后在启动Jconsole，找到并进入本地启动的main方法进程
 * 找到上方的MBean的tab，找到自定义的MBean，进行管理
 * 
 * @author zhangbo
 */
public class JMXTest {

    public static void main(String[] args) throws Exception {
        // 通过ManagementFactory获取MBeanServer
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        
        // 设置MBean的名称，规则为  域名:name=xxx
        ObjectName objectName = new ObjectName("jmxBean:name=hello");
        
        // 向MBeanServer注册自定义的MBean
        mBeanServer.registerMBean(new Hello(), objectName);

        TimeUnit.MINUTES.sleep(10);
    }
    
}
