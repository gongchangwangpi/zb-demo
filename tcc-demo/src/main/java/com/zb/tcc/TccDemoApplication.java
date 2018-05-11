package com.zb.tcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangbo
 */
@MapperScan(value = "com.zb.tcc.mappers")
@SpringBootApplication
public class TccDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(TccDemoApplication.class);
        
    }
    
}
