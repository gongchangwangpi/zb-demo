/**
 *
 */
package com.alibaba.ocean.rawsdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.client.policy.RequestPolicy;
import com.alibaba.ocean.rawsdk.client.policy.RequestPolicy.HttpMethodPolicy;
import com.alibaba.ocean.rawsdk.common.BizResultWrapper;
import com.alibaba.ocean.rawsdk.example.param.CBCRequestBody;
import com.alibaba.ocean.rawsdk.example.param.CommonBusinessCatParam;
import com.alibaba.ocean.rawsdk.example.param.CommonBusinessCatResult;


import java.io.IOException;
import java.util.UUID;

/**
 *
 *
 *
 * @author hongbang.hb
 *
 */
public class SdkExample {
    /**
     * It is an example to simulate the request which require accessToken.
     *
     *
     * @param apiExecutor
     * @param accessToken
     *
     */

    public void run(ApiExecutor apiExecutor, String accessToken) throws IOException {
        CommonBusinessCatParam param = new CommonBusinessCatParam();

        RequestPolicy oceanRequestPolicy = new RequestPolicy();
        oceanRequestPolicy.setHttpMethod(HttpMethodPolicy.POST)
                .setUseHttps(true).setUseSignture(true);
        param.setOceanRequestPolicy(oceanRequestPolicy);

        //业务参数
        CBCRequestBody body = new CBCRequestBody();
        body.setCategory_id(166);
        param.setBody(body);

        //请求随机ticket
        param.setTicket(UUID.randomUUID().toString().toUpperCase());
        // Calling and get the result.
        BizResultWrapper<CommonBusinessCatResult> result = apiExecutor.execute(param, accessToken);

        System.out.println("Result:" + JSON.toJSONString(result));

    }


    public static void main(String[] args) throws IOException {
        ApiExecutor apiExecutor = new ApiExecutor("43181021", "4MVsIqGZpi");

        SdkExample sdkExample = new SdkExample();
        sdkExample.run(apiExecutor, "ce4f1eaa-3bd7-4364-88c8-67211c4a36f6");
    }
}
