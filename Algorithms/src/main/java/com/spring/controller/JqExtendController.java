package com.spring.controller;

import com.test.chexian.api.dto.RestfulResultDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 */
@RequestMapping(value = "/jqExtend")
@RestController
public class JqExtendController {
    
    @GetMapping(value = "/get200")
    public RestfulResultDto get200() {
        return RestfulResultDto.success("200body");
    }
    @GetMapping(value = "/get500")
    public RestfulResultDto get500() {
        return RestfulResultDto.fail("500", "服务器出错啦，500");
    }
    @GetMapping(value = "/get401")
    public RestfulResultDto get401() {
        return RestfulResultDto.fail("401", "你还未登录，401");
    }
    @GetMapping(value = "/get")
    public RestfulResultDto get() {
        return RestfulResultDto.success();
    }
    
}
