package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author zhangbo
 * @date 2019-12-27
 */
@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    private HttpSession session;

    @PostMapping(value = "/login")
    public ResultDTO login(String username) {
        session.setAttribute("loginUser", username);
        return ResultDTO.success(session.getId());
    }

    @GetMapping(value = "/log")
    public ResultDTO log() {
        return ResultDTO.success();
    }

}
