/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.ocean.rawsdk.common;


import java.util.TimeZone;

/**
 * 淘宝访问协议常量
 *
 * @author wuwei 2020-4-25 上午9:53:40
 */
public class EleConstants {
    /**
     * 协议
     */
    public static final String ELE_PROTOCAL_SEPERATE = "_";

    public static final String ELE_PROTOCAL_URL = "/router/rest";

    public static final String ELE_PROTOCAL_DATE = "yyyy-MM-dd HH:mm:ss";

    public static final String ELE_PROTOCAL_ZONE = "GMT+8";

    public static final TimeZone ELE_TIMEZONE = TimeZone.getTimeZone(ELE_PROTOCAL_ZONE);
    /**
     * 公共参数
     */
    public static String API_NAME = "cmd";

    public static String APPKEY = "source";

    public static String ACCESS_TOKEN = "token";

    public static String TICKET = "ticket";

    public static String ENCRYPT = "encrypt";

    public static String VERSION = "version";

    public static String RESULT_DATA = "data";
    /**
     * yyyy-MM-dd
     */
    public static String TIMESTAMP = "timestamp";

    public static String SIGNATURE = "sign";

    public static String RESPONSE_SIMPLE = "simplify";

    public static String DEFAULT_NAMESPACE = "system";
    public static String NAMESPACE = "namespace";


    public static String DEFAULT_VERSION = "1";

    public static String SECRET = "secret";
    /**
     * 返回的错误格式
     */
    public static final String ELE_ERROR_RESPONSE = "body";

    public static final String ELE_ERROR_CODE = "errno";

    public static final String ELE_ERROR_MESSAGE = "error";

    /**
     * 正确返回格式
     */
    public static final String ELE_RESPONSE_SUFFIX = ELE_PROTOCAL_SEPERATE + "response";

    private static final String regularPattern = "\\.";

}
