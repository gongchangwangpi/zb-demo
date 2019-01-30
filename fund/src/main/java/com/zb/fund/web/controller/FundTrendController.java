package com.zb.fund.web.controller;

import com.zb.commons.dto.RestfulResultDto;
import com.zb.fund.service.fund.FundTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author zhangbo
 */
@RequestMapping(value = "/fund/trend")
@Controller
public class FundTrendController {
    
    @Autowired
    private FundTrendService fundTrendService;
    
    @GetMapping(value = "/sync")
    @ResponseBody
    public RestfulResultDto sync() {
        fundTrendService.saveFromEastMoney(new Date());
        return RestfulResultDto.succeed();
    }
    
}
