package com.test.chexian.api.encrypt;

import com.test.chexian.api.dto.RestfulRequestDto;
import com.test.chexian.api.dto.RestfulResultDto;
import com.test.chexian.api.util.AppId;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 数字签名工具类
 * 
 */
@Slf4j
public final class DigitalSignUtils {

	private DigitalSignUtils() {
		
	}
	
	
	public static String buildRequestSignText(RestfulRequestDto requestDto) {
		StringBuilder textBuilder = new StringBuilder("%" + requestDto.getAppId() + "^" + AppId.APP_SECRET + "&");
		
		//%appId^appSecret&requestTime*
		//%appId^appSecret&requestTime*
		textBuilder.append(requestDto.getRequestTime()).append("*");

		String body = requestDto.getBody();
		textBuilder.append(body);
		
		return textBuilder.toString();
	}
	
	public static String buildResponseSignText(RestfulResultDto resultDto) {
		StringBuilder textBuilder = new StringBuilder("%" + AppId.APP_ID + "^" + AppId.APP_SECRET + "&");
		
		//%appId^appSecret&加密body后得到的密文

		Object body = resultDto.getBody();
		if (body != null) {
			textBuilder.append(body);
		}
		
		return textBuilder.toString();
	}
	
	public static boolean validateResultDigitalSign(RestfulResultDto resultDto) {
		String signText = buildResponseSignText(resultDto);
		String sign = MessageDigestUtils.md5DigestAsHex(signText);
		
		return sign.equals(resultDto.getDigitalSign());
	}
	
}
