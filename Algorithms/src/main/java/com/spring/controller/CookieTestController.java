package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * cookie
 *
 * Created by books on 2017/9/19.
 */
@Controller
@RequestMapping(value = "/cookie")
public class CookieTestController {

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie1 = new Cookie("test", "testValue");
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("only", "httpOnlyValue");
        cookie2.setHttpOnly(true);
        response.addCookie(cookie2);

        return "cookie";
    }
    
    @RequestMapping(value = "/t")
    @ResponseBody
    public Date t(String orderCode, String goBack, HttpServletRequest request, HttpServletResponse response) {

        System.out.println(orderCode);
        System.out.println(goBack);
        
        return new Date();
    }

    @RequestMapping(value = "/json")
    @ResponseBody
    public Date json(Map<String, Object> map, HttpServletRequest request) {
        
//        System.out.println(Arrays.toString(ids));
//        System.out.println(code);
        System.out.println(map);
        
        return new Date();
    }
}
