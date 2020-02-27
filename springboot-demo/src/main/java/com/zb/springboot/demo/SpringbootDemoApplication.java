package com.zb.springboot.demo;

import com.zb.springboot.demo.service.email.MailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
//@MapperScan(value = "com.zb.springboot.demo.mapper")
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootDemoApplication.class, args);

        MailSenderUtil mailSenderUtil = context.getBean(MailSenderUtil.class);

        mailSenderUtil.sendSimpleMail();


    }

}
