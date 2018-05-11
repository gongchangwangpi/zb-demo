package com.spring.controller;

import com.alibaba.fastjson.JSON;
import com.spring.domain.ComplexRequest;
import com.test.chexian.api.dto.RestfulResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 复杂请求参数示例
 *
 * Created by Administrator on 2018/5/2 0002.
 */
@Slf4j
@RestController
public class ComplexRequestController {

    @RequestMapping(value = "complexRequest")
    public RestfulResultDto complexRequest(@RequestBody ComplexRequest complexRequest) {
        log.info("-------complexRequest :{}", JSON.toJSONString(complexRequest));

        return RestfulResultDto.success(null);
    }

}
