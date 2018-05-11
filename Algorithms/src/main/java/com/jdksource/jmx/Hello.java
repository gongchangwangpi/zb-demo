package com.jdksource.jmx;

/**
 * 该类名名称必须与父接口的名称前缀一致
 * 
 * @author zhangbo
 */
public class Hello implements HelloMBean {
    
    private String name;
    
    @Override
    public String getName() {
        System.out.println("getName : " + name);
        return name;
    }

    @Override
    public void setName(String name) {
        System.out.println("setName : " + name);
        this.name = name;
    }
}
