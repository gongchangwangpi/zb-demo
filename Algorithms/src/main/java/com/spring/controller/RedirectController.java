package com.spring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangbo
 */
@Slf4j
@Controller
@RequestMapping(value = "/redirect")
public class RedirectController {
    
    @RequestMapping(value = "/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        log.info("index -- set session: index = index_1");
        
        request.getSession().setAttribute("index", "index_1");
        response.sendRedirect("http://127.0.0.1/html/redirect.html");
    }
    
    @RequestMapping(value = "/redirect")
    public void t(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().setAttribute("redirect", "redirect_2");
        
        String url = "http://127.0.0.1:8080/api/t";
        log.info("redirect to : {}", url);  
        
        response.sendRedirect(url);
    }
    
    @RequestMapping(value = "/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Object index = request.getSession().getAttribute("index");
        Object redirect = request.getSession().getAttribute("redirect");

        log.info("callback get session: index = {}, redirect = {}", index, redirect);
        
        response.sendRedirect("http://127.0.0.1/html/redirect.html");
    }
}
