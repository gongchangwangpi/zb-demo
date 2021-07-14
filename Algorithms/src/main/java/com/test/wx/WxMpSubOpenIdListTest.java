package com.test.wx;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

/**
 * @author zhangbo
 * @date 2020/12/15
 */
@Slf4j
public class WxMpSubOpenIdListTest {

    public static void main(String[] args) throws Exception {
        // oqBpd1HQcQ349yv1JnOai8c5fbbI,oqBpd1CGeZuovmnLpXwz29mQlRX4
        //

        String appId = "wx0fda3d9926bd6d9f";

        String token = "40_h0xxPJBEZlwEFijuDNsEtwVHtPH6hV8-fufnpjo52U65d7B4-VHXbWAj-TwgS39B8DKeE-1Y2NW28dGcLBYc5s_KJbsVFQL0YWLyc4kCAq_9za8MpE7hjfgYfmX_5r-U4WWP27QNkOBWOpcgVLLaAKDNDD";
        // next_openid为分页的下一个openid，默认每次查询10000条
        WxMpService wxMpService = WxServiceUtil.getWxMpService(appId, token);
        WxMpUserService userService = wxMpService.getUserService();

        WxMpUserList wxMpUserList = userService.userList(null);
        for (String openId : wxMpUserList.getOpenids()) {
            try {
                WxMpUser wxMpUser = userService.userInfo(openId);
                if (wxMpUser.getNickname().contains("张玻")) {
                    log.info(JSON.toJSONString(wxMpUser));
                    return;
                }
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }

}
