package com.zb.fund.web.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zb.fund.web.serialize.DateDeserializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhangbo
 * @date 2019-08-15
 */
@Slf4j
@RestController
public class DatestampController {

    @GetMapping(value = "/datetime")
    public QueryReq datetimeGet(QueryReq queryReq, Date paramDate) {
        log.info("query = {}", JSON.toJSONString(queryReq));
        log.info("paramDate = {}", paramDate);
        return queryReq;
    }

    @PostMapping(value = "/datetime")
    public QueryReq datetimePost(@RequestBody QueryReq queryReq) {
        log.info("query = {}", JSON.toJSONString(queryReq));
        return queryReq;
    }

    @Getter
    @Setter
    private static class QueryReq {
        @JsonDeserialize(using = DateDeserializer.class)
        private java.util.Date utilDate;
        private java.sql.Date sqlDate;
        private java.sql.Timestamp timestamp;
        private Long longTimestamp;
    }

}
