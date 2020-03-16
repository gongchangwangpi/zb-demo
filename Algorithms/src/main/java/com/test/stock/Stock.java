package com.test.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangbo
 * @date 2020/3/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    /**
     * 股票编码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 股息
     */
    private double dividend;
    /**
     * TTM市盈率
     */
    private double ttmPeRatio;
    /**
     * 现价
     */
    private double nowPrice;
    /**
     * 10年期国债收益率
     */
    private double nationalDebt = GoodPrice.DEFAULT_NATIONAL_DEBT;

}
