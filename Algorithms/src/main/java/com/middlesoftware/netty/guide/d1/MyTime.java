package com.middlesoftware.netty.guide.d1;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyTime implements Serializable {
    
    private static final long serialVersionUID = -6920539250987688184L;
    
    private static AtomicInteger count = new AtomicInteger();
    
    private String name = "time: " + count.getAndIncrement();
    
    private long timestamp = System.currentTimeMillis();
    
}
