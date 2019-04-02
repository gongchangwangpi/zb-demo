package com.spring.controller;

import com.spring.domain.User;
import com.spring.domain.query.UserQuery;
import com.spring.service.UserService;
import com.test.chexian.api.dto.RestfulResultDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by books on 2017/4/28.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/update")
    @ResponseBody
    public User update() {
        User user = new User();
        user.setId(1L);
        user.setUsername("zhangsan");

        userService.update1(user);

        return user;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public User save(User user) {
        userService.save(user);
        return user;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<User> list(UserQuery query) {
        return userService.selectListByQuery(query);
    }
    
    @RequestMapping(value = "/batchSave")
    @ResponseBody
    public List<User> batchSave(@RequestBody List<User> users) {
        return users;
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public RestfulResultDto get() {
        return RestfulResultDto.success(userService.selectById(1L));
    }
}
