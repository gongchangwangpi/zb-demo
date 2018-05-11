package com.spring.controller;

import com.test.chexian.api.dto.RestfulResultDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP method应分为安全性，幂等性
 * 其中GET, HEAD, TRACE, OPTIONS 为安全的操作，同样也是幂等的
 * PUT被要求实现为幂等的，DELETE也应是幂等的
 * 
 * 如果PUT请求的资源在服务端没有，则应该新建对应的资源；如已存在，则应修改为请求中的资源信息
 * GET,HEAD应总是返回相同的内容。但HEAD不返回消息体，只包含消息头，主要用于检查资源是否可用，缓存等
 * 
 * TRACE请求不得包含实体。TRACE允许客户端查看请求链另一端收到的内容，并将该数据用于测试或诊断信息。 
 * Via头域的值（14.45节）特别引人关注，因为它充当请求链的轨迹。 
 * Max-Forwards头字段的使用允许客户端限制请求链的长度，这对于在无限循环中测试代理转发消息链很有用。
 * 
 * 
 * 
 * @author zhangbo
 */
@Slf4j
@RestController
public class HttpMethodController {
    
    @RequestMapping(value = "/method", method = RequestMethod.GET)
    public RestfulResultDto methodGet(String param) {
        log.info("----- GET -----: {}", param);
        return RestfulResultDto.success("GET");
    }

    @RequestMapping(value = "/method", method = RequestMethod.HEAD)
    public RestfulResultDto methodHead(String param) {
        log.info("----- HEAD -----: {}", param);
        return RestfulResultDto.success("HEAD");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.POST)
    public RestfulResultDto methodPost(String param) {
        log.info("----- POST -----: {}", param);
        return RestfulResultDto.success("POST");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.PUT)
    public RestfulResultDto methodPut(String param) {
        log.info("----- PUT -----: {}", param);
        return RestfulResultDto.success("PUT");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.DELETE)
    public RestfulResultDto methodDelete(String param) {
        log.info("----- DELETE -----: {}", param);
        return RestfulResultDto.success("DELETE");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.TRACE)
    public RestfulResultDto methodGetTrace(String param) {
        log.info("----- TRACE -----: {}", param);
        return RestfulResultDto.success("TRACE");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.PATCH)
    public RestfulResultDto methodGetPatch(String param) {
        log.info("----- PATCH -----: {}", param);
        return RestfulResultDto.success("PATCH");
    }
    
    @RequestMapping(value = "/method", method = RequestMethod.OPTIONS)
    public RestfulResultDto methodGetOptions(String param) {
        log.info("----- OPTIONS -----: {}", param);
        return RestfulResultDto.success("OPTIONS");
    }
    
}
