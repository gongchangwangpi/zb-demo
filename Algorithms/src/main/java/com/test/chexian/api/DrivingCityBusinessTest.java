package com.test.chexian.api;

import com.test.chexian.api.util.HttpPostUtil;
import com.test.chexian.api.dto.RestfulRequestDto;

/**
 * @author books
 */
public class DrivingCityBusinessTest {

    public static void main(String[] args) {

        RestfulRequestDto requestDto = new RestfulRequestDto(true);

        requestDto.setBody("{\"cityCode\":\"510100\"}");

        HttpPostUtil.postCarInsApi("/cfg/getCityBusinesses", requestDto);
    }
    
}
