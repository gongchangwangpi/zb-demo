package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangbo
 * @date 2019-10-28
 */
@RestController
public class PostController {

    @PostMapping(value = "/post/{path}")
    public User post(@PathVariable String path, String username,
                     @Validated @RequestBody User user, HttpServletResponse response) throws IOException {
//        response.sendRedirect("http://www.baidu.com");

        if (user.getId() == null) {
            throw new IllegalArgumentException("id为空");
        }

        return user;
    }

}
