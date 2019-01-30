package com.zb.fund.service.remote.impl;

import com.alibaba.fastjson.JSON;
import com.zb.commons.date.DateUtil;
import com.zb.commons.http.SimpleHttpClient;
import com.zb.fund.dto.ResponseDto;
import com.zb.fund.service.remote.EastMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
@Slf4j
@Service(value = "eastMoneyService")
public class EastMoneyServiceImpl implements EastMoneyService {

    @Value("${eastMoney.server}")
    private String url;

    @Autowired
    private SimpleHttpClient httpClient;

    @Override
    public ResponseDto get(String fundTypeCode, Date statDate) {
        // 默认查询全部
        fundTypeCode = StringUtils.defaultIfEmpty(fundTypeCode, "all");
        // 默认今天
        statDate = statDate == null ? new Date() : statDate;
        String statisticsDateStr = DateUtil.defaultFormatDate(statDate);

        String queryUrl = String.format(url, fundTypeCode, statisticsDateStr, statisticsDateStr);

        String response = httpClient.get(queryUrl);

//        response = response.substring(response.indexOf("["));
//        response = response.substring(0, response.indexOf("]") + 1);
//
//        ResponseDto responseDto = new ResponseDto();
//        List<String> datas = JSON.parseArray(response, String.class);
//        responseDto.setDatas(datas);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        
        try {
            
            engine.eval(response);
            Object rankData = engine.get("rankData");
            
            return JSON.parseObject(JSON.toJSONString(rankData), ResponseDto.class);
            
        } catch (ScriptException e) {
            log.error("解析js失败", e);
        }

        return null;
    }
}
