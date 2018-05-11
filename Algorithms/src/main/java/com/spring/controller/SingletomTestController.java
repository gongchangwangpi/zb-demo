package com.spring.controller;

import com.spring.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public class SingletomTestController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public Object test1() {
        return new Date();
    }
}
