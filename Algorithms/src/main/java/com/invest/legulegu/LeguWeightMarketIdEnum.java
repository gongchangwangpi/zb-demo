package com.invest.legulegu;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author bo6.zhang
 * @date 2021/7/14
 */
@Getter
@AllArgsConstructor
public enum LeguWeightMarketIdEnum {

    SHANG_ZHENG_50("000016.SH", "上证50"),
    HU_SHEN_300("000300.SH", "沪深300"),
    ZHONG_ZHENG_500("000905.SH", "中证500"),
    CHUANG_YE_BAN("399006.SZ", "创业板指"),
    SHEN_ZHEN_CHENG_ZHI("399001.SZ", "深证成指"),
    DEFAULT("-1", "");

    private String code;
    private String desc;

    public static LeguWeightMarketIdEnum get(String code) {
        return Stream.of(LeguWeightMarketIdEnum.values()).filter(e -> e.code.equalsIgnoreCase(code)).findFirst().orElse(DEFAULT);
    }
}
