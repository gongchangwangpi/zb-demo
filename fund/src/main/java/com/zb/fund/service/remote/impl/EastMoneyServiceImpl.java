package com.zb.fund.service.remote.impl;

import com.alibaba.fastjson.JSON;
import com.zb.commons.date.DateUtil;
import com.zb.commons.http.SimpleHttpClient;
import com.zb.commons.json.JacksonJsonMapper;
import com.zb.fund.dto.ResponseDto;
import com.zb.fund.service.remote.EastMoneyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
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

        response = response.substring(response.indexOf("["));
        response = response.substring(0, response.indexOf("]") + 1);

        ResponseDto responseDto = new ResponseDto();
        List<String> datas = JSON.parseArray(response, String.class);
        responseDto.setDatas(datas);
        
        return responseDto;
    }
}
