package com.zb.tcc.web.controller;

import javax.validation.Valid;

import com.zb.demo.util.json.JacksonJsonMapper;
import com.zb.tcc.domain.user.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 */
@Slf4j
@RestController
public class TestController {
    
    @RequestMapping(value = "/test")
    public Object test(@RequestParam int type) {
        log.info("type: {}", type);  
        
        return System.currentTimeMillis();
    }
    
    @RequestMapping(value = "/user")
    public Object user(@Valid User user) {
        log.info("--->>> : {}", JacksonJsonMapper.INSTANCE.toJson(user));
        
        return "user";
    }
}
