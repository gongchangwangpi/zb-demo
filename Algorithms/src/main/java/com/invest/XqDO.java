package com.invest;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/3/2
 */
@Data
public class XqDO {

    private String symbol;
    private LocalDate date;
    private double volume;
    private double open;
    private double high;
    private double low;
    private double close;
    private String chg;
    private String percent;
    private String turnoverrate;
    private String amount;
    private String volume_post;
    private String amount_post;
    private String pe;
    private String pb;
    private String ps;
    private String pcf;
    private String market_capital;
    private String balance;
    private String hold_volume_cn;
    private String hold_ratio_cn;
    private String net_volume_cn;
    private String hold_volume_hk;
    private String hold_ratio_hk;
    private String net_volume_hk;

    private Map<Integer, Double> avgMap = new HashMap<>();
//    private double avg5;
//    private double avg10;
//    private double avg20;
//    private double avg60;
//    private double avg120;
//    private double avg250;

}
