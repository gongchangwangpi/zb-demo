package com.spring.controller;

import com.util.SimpleHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by books on 2017/9/5.
 */
@Controller
@RequestMapping(value = "/http")
public class HttpClientTestController {

    private Logger logger = LoggerFactory.getLogger(HttpClientTestController.class);

    @Resource
    private SimpleHttpClient simpleHttpClient;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {
        for (int i = 0; i < 10; i++) {
            String result = simpleHttpClient.get("http://127.0.0.1/http/test1");
        }
        return "success";
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1() {
        return new Date().toString();
    }

}
