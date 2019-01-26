package com.zb.fund.service.remote;

import com.zb.fund.dto.ResponseDto;

import java.util.Date;

/**
 * Created by Administrator on 2019/1/26 0026.
 */
public interface EastMoneyService {

    /**
     * 获取某天的基金数据
     * @param fundTypeCode
     * @param statDate
     * @return
     */
    ResponseDto get(String fundTypeCode, Date statDate);

}
