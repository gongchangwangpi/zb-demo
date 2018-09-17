package com.zb.demo.util.system;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author zhangbo
 */
@Slf4j
public class PidUtils {
    
    public static int getPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName(); // format: "pid@hostname"
            if (log.isInfoEnabled()) {
                log.info("runtime process name = {}", name);
            }
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Throwable e) {
            log.warn("get pid error", e);
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(getPid());
    }
}
