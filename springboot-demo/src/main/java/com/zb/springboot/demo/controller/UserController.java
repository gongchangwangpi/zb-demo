package com.zb.springboot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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

    @GetMapping(value = "/txPropagationTest")
    public Long txPropagationTest() throws Exception {
        return userService.insertPropagation();
    }

    @PostMapping(value = "/save")
    public Long save(@RequestBody User user) {
        return userService.insert(user);
    }

    @PostMapping(value = "/batchSave")
    public int batchSave() {
        User user = new User();
        user.setUsername("user" + RandomStringUtils.random(5));
        user.setAge(11);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());
        User user2 = new User();
        user2.setUsername("user2" + RandomStringUtils.random(5));
        user2.setAge(12);
        user2.setCreateTime(new Date());
        user2.setRegisterTime(LocalDateTime.now());

        ArrayList<User> users = Lists.newArrayList(user, user2);
        int insert = userService.batchInsert(users);
        System.out.println(JSON.toJSONString(users));

        return insert;
    }
}
