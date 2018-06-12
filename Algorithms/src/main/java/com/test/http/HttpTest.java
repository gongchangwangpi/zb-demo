package com.test.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangbo
 */
public class HttpTest {

    public static void main(String[] args) {
        
        String url = "http://127.0.0.1:11222/vehicleInsurance/endorse";
        
        List<Map<String, String>> params = new ArrayList<>();

        Map<String, String> param1 = new HashMap<>();
        param1.put("requestType", "1");
        param1.put("policyNo", "0602100007410812201800002");
        param1.put("endorNo", "2");
        param1.put("withdrawMoney", "3329.64");
        param1.put("totalPremium", "0");
        
        Map<String, String> param2 = new HashMap<>();
        param2.put("requestType", "1");
        param2.put("policyNo", "SYX20180418001");
        param2.put("endorNo", "3");
        param2.put("withdrawMoney", "1");
        param2.put("totalPremium", "1");
        
        params.add(param1);
        params.add(param2);
        
        HttpPostUtil.postJson(url, JSON.toJSONString(params));
        
    }
    
}
