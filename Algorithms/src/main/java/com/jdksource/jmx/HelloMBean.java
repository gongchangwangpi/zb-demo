package com.jdksource.jmx;

/**
 * 定义MBean接口，接口名称一定要符合规范，以MBean结尾
 * 
 * @author zhangbo
 */
public interface HelloMBean {
    
    String getName();
    
    void setName(String name);
    
}
