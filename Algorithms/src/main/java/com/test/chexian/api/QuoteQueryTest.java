package com.test.chexian.api;

import com.alibaba.fastjson.JSON;
import com.test.chexian.api.dto.QuoteQueryRequestDto;
import com.test.chexian.api.dto.RestfulRequestDto;
import com.test.chexian.api.util.HttpPostUtil;

/**
 * @author books
 */
public class QuoteQueryTest {

    public static void main(String[] args) {

        RestfulRequestDto requestDto = new RestfulRequestDto(true);

        QuoteQueryRequestDto quoteQueryRequest = new QuoteQueryRequestDto();
        quoteQueryRequest.setOperatorMobile("18683367717");
        quoteQueryRequest.setQuoteReqId(1416L);
        
        requestDto.setBody(JSON.toJSONString(quoteQueryRequest));

        HttpPostUtil.postCarInsApi("/quote/getResult", requestDto);
    }
    
}
