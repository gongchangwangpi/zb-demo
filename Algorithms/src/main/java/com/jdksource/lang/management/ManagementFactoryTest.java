package com.jdksource.lang.management;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

/**
 * @author zhangbo
 */
public class ManagementFactoryTest {

    public static void main(String[] args) {

        List<GarbageCollectorMXBean> gcMXBean = ManagementFactory.getGarbageCollectorMXBeans();
        gcMXBean.forEach(gc -> System.out.println(gc.getName()));

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(operatingSystemMXBean.getName());
        System.out.println(operatingSystemMXBean.getArch());

    }
    
}
