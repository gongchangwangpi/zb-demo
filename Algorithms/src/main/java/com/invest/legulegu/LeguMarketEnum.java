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
public enum LeguMarketEnum {

    SHANG_HAI("1", "上证"),
    SHEN_ZHEN("2", "深证"),
    ZHONG_XIAO_BAN("3", "中小100"),
    CHUANG_YE_BAN("4", "创业板"),
    HU_SHEN_300("000300.XSHG", "沪深300"),
    DEFAULT("-1", "");

    private String code;
    private String desc;

    public static LeguMarketEnum get(String code) {
        return Stream.of(LeguMarketEnum.values()).filter(e -> e.code.equalsIgnoreCase(code)).findFirst().orElse(DEFAULT);
    }
}
