package com.dubbo.test.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author zhangbo
 */
@SPI(value = "impl1")
public interface ISayHi {
    
    void say();
    
}
