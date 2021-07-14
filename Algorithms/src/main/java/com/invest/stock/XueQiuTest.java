package com.invest.stock;

import com.test.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/3/16
 **/
public class XueQiuTest {

    private static String URL = "https://stock.xueqiu.com/v5/stock/quote.json?symbol=%s&extend=detail";

    public static void main(String[] args) {

        String geli = "SZ000651";

        String url = String.format(URL, geli);

        Map<String, String> headers = new HashMap<>();
//        Host: stock.xueqiu.com
//        Origin: https://xueqiu.com
//        Referer: https://xueqiu.com/S/SZ000651
        headers.put("Host", "stock.xueqiu.com");
        headers.put("Origin", "https://xueqiu.com");
        headers.put("Referer", "https://xueqiu.com/S/SZ00651");

        String response = HttpUtil.get(url, headers, String.class);

        System.out.println(response);

    }

}
