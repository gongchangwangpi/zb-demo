package com.zb.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangbo
 * @date 2019-10-28
 */
@Controller
public class PostController {

    @PostMapping(value = "/post")
    public void post(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://www.baidu.com");
    }

}
