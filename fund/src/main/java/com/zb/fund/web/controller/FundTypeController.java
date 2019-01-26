package com.zb.fund.web.controller;

import com.zb.commons.dto.RestfulResultDto;
import com.zb.fund.service.fund.FundTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
@RestController
public class FundTypeController {

    @Autowired
    private FundTypeService fundTypeService;

    @GetMapping(value = "/fundTypes")
    public RestfulResultDto fundTypes() {
        return RestfulResultDto.succeed(fundTypeService.list());
    }

}
