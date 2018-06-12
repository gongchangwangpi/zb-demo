package com.test.chexian.loan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.test.chexian.api.dto.RestfulRequestDto;
import com.test.chexian.api.util.HttpPostUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 车险保单贷批单接口
 * 
 * @author books
 */
@Slf4j
public class EndorseTest {

    public static void main(String[] args) {

        RestfulRequestDto requestDto = new RestfulRequestDto(true);

        List<Map<String, String>> params = new ArrayList<>();
        
        Map<String, String> param = new HashMap<>();
        param.put("requestType", "1");
        param.put("endorNo", "c20180608163705001");
        param.put("totalPremium", "0");
        param.put("policyNo", "123456789");
        param.put("withdrawMoney", "1000");
        
        params.add(param);
        
        requestDto.setBody(JSON.toJSONString(params));

        HttpPostUtil.postEncode("/policy-loan-data-origin-api/vehicleInsurance/endorse", requestDto);
        
    }
    
    
}
