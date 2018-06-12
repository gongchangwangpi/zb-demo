package com.test.chexian.loan;

import com.test.chexian.api.dto.RestfulRequestDto;
import com.test.chexian.api.util.HttpPostUtil;

/**
 * @author zhangbo
 */
public class QueryTest {

    public static void main(String[] args) {

        RestfulRequestDto requestDto = new RestfulRequestDto(true);

        HttpPostUtil.postEncode("/policy-loan-data-origin-api/vehicleInsurance/list", requestDto);
    }
    
}
