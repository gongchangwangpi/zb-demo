package com.spring.ratelimit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 控制器
 * 
 * Created by books on 2017/12/21.
 */
@RestController
public class TestController {
    
    @RequestMapping(value = "limit1")
    public String limit1(HttpServletResponse response) {
        return "OK - limit1";
    }
    
    @RequestMapping(value = "limit2")
    public String limit2(HttpServletResponse response) {
        return "OK - limit2";
    }
    
}
