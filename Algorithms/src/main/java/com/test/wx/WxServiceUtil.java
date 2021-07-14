package com.test.wx;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangbo
 * @date 2020/12/15
 */
public class WxServiceUtil {

    private static final String DEFAULT_APPID = "";
    private static final String DEFAULT_TOKEN = "";

    public static WxMpService getWxMpService(String appId, String token) {
        WxMpDefaultConfigImpl wxMaConfig = new WxMpDefaultConfigImpl();
        wxMaConfig.setAppId(StringUtils.defaultIfEmpty(appId, DEFAULT_APPID));
        wxMaConfig.setAccessToken(StringUtils.defaultIfEmpty(token, DEFAULT_TOKEN));
        wxMaConfig.setExpiresTime(System.currentTimeMillis() + 60 * 1000L);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMaConfig);
        return wxMpService;
    }

}
