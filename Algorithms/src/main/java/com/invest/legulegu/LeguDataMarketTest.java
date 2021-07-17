package com.invest.legulegu;


import com.alibaba.fastjson.JSON;
import com.invest.HeaderUtil;
import com.invest.entity.MarketTransDataDO;
import com.invest.mapper.MarketTransDataMapper;
import com.invest.util.MybatisUtil;
import com.util.http.SimpleHttpClient;
import com.util.http.SimpleHttpsClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bo6.zhang
 * @date 2021/7/14
 */
public class LeguDataMarketTest {

    public static void main(String[] args) {

        Map<String, String> header = HeaderUtil.userAgent();

        SimpleHttpClient.HResp hResp = SimpleHttpsClient.getHRespByHeaders("https://www.legulegu.com/", header);

        Map<String, String> headers = HeaderUtil.headers(hResp.getHeaders());

        String url = "https://www.legulegu.com/api/stock-data/market-pe?marketId=4&token=0694b1714768f441a6827c5776da3cdc";

        String peResp = SimpleHttpsClient.get(url);

        System.out.println(peResp);

        LeguPeMarketResp leguPeMarketResp = JSON.parseObject(peResp, LeguPeMarketResp.class);

        List<LeguPeMarketResp.LeguPeData> data = leguPeMarketResp.getData();

        List<MarketTransDataDO> list = data.stream().map(LeguPeMarketResp::convert).collect(Collectors.toList());

        MarketTransDataMapper mapper = MybatisUtil.getMapper(MarketTransDataMapper.class);
        mapper.batchInsert(list);

        MybatisUtil.getSqlSession().commit();

    }

}
