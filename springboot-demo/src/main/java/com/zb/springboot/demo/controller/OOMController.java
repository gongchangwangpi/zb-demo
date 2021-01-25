package com.zb.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/1/25
 */
@RestController
public class OOMController {

    private List<byte[]> list = new ArrayList<>();

    @GetMapping(value = "/oom/{mb}")
    public Long oom(@PathVariable("mb") Integer mb) {
        list.add(new byte[1024 * 1024 * mb]);
        return System.currentTimeMillis();
    }

}
