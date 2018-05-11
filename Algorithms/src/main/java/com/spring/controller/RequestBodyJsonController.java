package com.spring.controller;

import com.alibaba.fastjson.JSON;
import com.spring.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestBodyDto 接受JSON参数
 * 
 * Created by books on 2017/12/27.
 */
@RestController
public class RequestBodyJsonController {

    private Logger logger = LoggerFactory.getLogger(RequestBodyJsonController.class);
    
    @RequestMapping(value = "/requestBody/body", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User body(@RequestBody User user, HttpServletRequest request) {
        logger.info("body ------> " + JSON.toJSONString(user));
        return user;
    }
    
    @RequestMapping(value = "/requestBody/attr", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User attr(@RequestAttribute User user, HttpServletRequest request) {
        logger.info("attr ------> " + JSON.toJSONString(user));
        return new User();
    }
    
}
