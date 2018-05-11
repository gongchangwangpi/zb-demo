package com.test.chexian.api.dto;

import com.test.chexian.api.util.AppId;
import com.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 针对API请求的数据交互对象
 * 
 * @author Edison Lyu
 *
 */
@Getter
@Setter
public class RestfulRequestDto<T extends RequestBodyDto> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8156344850835366252L;
	
	//第三方合作伙伴ID对应的请求参数名
	public static final String APP_ID_NAME = "appId";

	//请求时间对应的请求参数名
	public static final String REQ_TIME_NAME = "requestTime";
	
	//请求时间的FORMAT
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	//数字签名对应的请求参数名
	public static final String DIGITAL_SIGN_NAME = "digitalSign";

	/**
	 * 第三方合作伙伴ID
	 */
	private String appId;
	
	private String appKey;
	
	private String appSecret;
	
	/**
	 * 请求时间.形如'2017-12-16 10:00:00.000'
	 */
	private String requestTime;
	
	/**
	 * 数字签名
	 */
	private String digitalSign;
	
	/**
	 * 涉及具体业务API的请求参数
	 */
	private String body;
	
	private T data;

	public RestfulRequestDto() {
	}
	
	public RestfulRequestDto(boolean init) {
		if (init) {
			this.appId = AppId.APP_ID;
//			this.appSecret = AppId.APP_SECRET;
			this.requestTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
		}
	}
	
}
