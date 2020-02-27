package com.zb.mongo.demo.controller;

import com.zb.mongo.demo.domain.Blog;
import com.zb.mongo.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangbo
 * @date 2020-02-20
 */
@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/author/{author}")
    public List<Blog> byAuthor(@PathVariable("author") String author) {
        return blogService.findByAuthor(author);
    }

    @GetMapping(value = "/page")
    public List<Blog> page(int page, int size) {
        return blogService.page(page, size);
    }

}
