package com.test.chexian.api.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * 针对API响应的数据交互对象
 * 
 * @author Edison Lyu
 *
 */
@Data
public class RestfulResultDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156344850835366252L;

	/**
	 * 编码.如'200'
	 */
	private String code;
	
	/**
	 * 编码对应的文本信息.如'200'对应'OK'
	 */
	private String message;
	
	/**
	 * 业务数据
	 */
	private Object body;
	
	/**
	 * 数字签名
	 */
	private String digitalSign;
	
	private long timestamp;

	public RestfulResultDto() {
		super();
		this.timestamp = System.currentTimeMillis();
	}

	public RestfulResultDto(String code, String message) {
		super();
		this.code = code;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	public RestfulResultDto(String code, String message, Object body) {
		super();
		this.code = code;
		this.message = message;
		this.body = body;
		this.timestamp = System.currentTimeMillis();
	}

	public static RestfulResultDto success(Object body) {
		return new RestfulResultDto("200", "OK", body);
	}
}
