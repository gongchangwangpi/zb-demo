package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangbo
 * @date 2020/9/9
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/{id}")
    public User get(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping(value = "/txIsolationTest")
    public User txIsolationTest() {
        return userService.txIsolationTest();
    }

}
