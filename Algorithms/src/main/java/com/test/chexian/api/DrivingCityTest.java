package com.test.chexian.api;

import com.test.chexian.api.util.HttpPostUtil;
import com.test.chexian.api.dto.RestfulRequestDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author books
 */
@Slf4j
public class DrivingCityTest {

    public static void main(String[] args) {

        RestfulRequestDto requestDto = new RestfulRequestDto(true);

        HttpPostUtil.postEncode("/cfg/getDrivingCities", requestDto);
    }
    
}
