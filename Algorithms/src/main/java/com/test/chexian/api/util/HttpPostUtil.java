package com.test.chexian.api.util;

import com.alibaba.fastjson.JSON;
import com.test.chexian.api.dto.RestfulRequestDto;
import com.test.chexian.api.dto.RestfulResultDto;
import com.util.SimpleHttpClient;
import com.test.chexian.api.encrypt.DigitalSignUtils;
import com.test.chexian.api.encrypt.MessageDigestUtils;
import com.test.chexian.api.encrypt.SymmetricEncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author books
 */
@Slf4j
public class HttpPostUtil {
    
//    private static final String CAR_INS_SERVER = "http://localhost:8080/car_ins_api";
    private static final String SERVER_URL = "http://localhost:9999/api-gateway";
//    private static final String SERVER_URL = "http://test.jhjhome.com/api-gateway";
//    private static final String SERVER_URL = "http://127.0.0.1:11222";
    
    public static void postEncode(String url, RestfulRequestDto requestDto) {

        String requestDtoBody = requestDto.getBody();
        if (StringUtils.isNotEmpty(requestDtoBody)) {
            String decrypt = SymmetricEncryptionUtils.aesEncrypt(AppId.APP_KEY, requestDtoBody);
            requestDto.setBody(decrypt);
        }

        String signText = DigitalSignUtils.buildRequestSignText(requestDto);

        String sign = MessageDigestUtils.md5DigestAsHex(signText);

        requestDto.setDigitalSign(sign);
        
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.init();
        url = SERVER_URL + url;

        String param = JSON.toJSONString(requestDto);

        String res = simpleHttpClient.post(url, param);

        RestfulResultDto resultDto = JSON.parseObject(res, RestfulResultDto.class);

        Object body = resultDto.getBody();
        if (body != null) {
            String bodyStr = SymmetricEncryptionUtils.aesDecrypt(AppId.APP_KEY, JSON.toJSONString(body));
            log.info("response body : {}", bodyStr);
        }
    }
    
}
