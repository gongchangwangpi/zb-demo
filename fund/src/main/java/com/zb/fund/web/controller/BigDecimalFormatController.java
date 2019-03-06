package com.zb.fund.web.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zb.commons.dto.RestfulResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author zhangbo
 */
@Validated
@RestController
public class BigDecimalFormatController {
    
    @RequestMapping(value = "decimal/format")
    public RestfulResultDto format(@NotEmpty(message = "money must not be empty") String money) {
        return RestfulResultDto.succeed(new User("zhangsan", new BigDecimal(money)));
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        @NumberFormat(pattern = "0.00")
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal money;
    }
}
