package com.zb.shardingjdbc.controller;

import com.zb.shardingjdbc.domain.User;
import com.zb.shardingjdbc.dto.ResultDTO;
import com.zb.shardingjdbc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 * @date 2020-02-08
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/{id}")
    public ResultDTO get(@PathVariable("id") Long id) {
        return ResultDTO.success(userService.get(id));
    }

    @PostMapping
    public ResultDTO save(@RequestBody User user) {
        userService.save(user);
        return ResultDTO.success(user);
    }

}
