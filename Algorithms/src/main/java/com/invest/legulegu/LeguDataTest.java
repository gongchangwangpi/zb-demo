package com.invest.legulegu;


import com.invest.HeaderUtil;
import com.util.http.SimpleHttpClient;
import com.util.http.SimpleHttpsClient;

import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/7/14
 */
public class LeguDataTest {

    public static void main(String[] args) {

        Map<String, String> header = HeaderUtil.userAgent();

        SimpleHttpClient.HResp hResp = SimpleHttpsClient.getHRespByHeaders("https://www.legulegu.com/", header);

        Map<String, String> headers = HeaderUtil.headers(hResp.getHeaders());

        String url = "https://www.legulegu.com/api/stock-data/market-pe?marketId=1&token=28dec0f84f04f6a12caf0dfc21aceb73";

        String peResp = SimpleHttpsClient.get(url);

        System.out.println(peResp);


    }

}
