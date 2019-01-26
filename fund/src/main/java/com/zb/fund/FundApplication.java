package com.zb.fund;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.zb.fund.mapper")
@SpringBootApplication
public class FundApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundApplication.class, args);
	}

}

