package com.test.chexian.api.encrypt;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;


/**
 * 
 * 对称加密工具类
 * 
 * @author Edison Lyu
 *
 */
public final class SymmetricEncryptionUtils {

	private SymmetricEncryptionUtils() {

	}

	/**
	 * 
	 * 基于AES的加密
	 * 
	 * @param key         密钥,长度固定为16.理论上来说可以为8的整数倍.
	 * @param message     明文数据
	 * @return encrypted string
	 */
	public static String aesEncrypt(String key, String message) {
		
		if (!keyValidate(key)) {
			return null;
		}
		
		try {
			//创建密钥
			Key secretKey = new SecretKeySpec(key.getBytes(CharEncoding.UTF_8), "AES");
			//算法/模式/补码方式
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			//加密(AES)
			byte[] result = cipher.doFinal(message.getBytes(CharEncoding.UTF_8));
			
//			return result == null ? null : Hex.encodeHexString(result);
			
			//BASE64编码
			return DatatypeConverter.printBase64Binary(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("aes encrypt failed");
		}
		
	}

	/**
	 * 
	 * 基于AES的解密
	 * 
	 * @param key            密钥,长度固定为16.理论上来说可以为8的整数倍.
	 * @param encryptedMsg   密文数据
	 * @return
	 */
	public static String aesDecrypt(String key, String encryptedMsg) {

		if (!keyValidate(key)) {
			return null;
		}
		
		try {
			//BASE64解码
			byte[] encrypted = DatatypeConverter.parseBase64Binary(encryptedMsg);
			
			//创建密钥
			Key secretKey = new SecretKeySpec(key.getBytes(CharEncoding.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			
			//解密(AES)
			byte[] original = cipher.doFinal(encrypted);
			return new String(original);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("aes decrypt failed");
		}
	}
	
	private static boolean keyValidate(String key) {
		if (StringUtils.isEmpty(key) || StringUtils.EMPTY.equals(key.trim()) || key.length() != 16) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String message = "{\"success\":\"1\",\"result\":{\"date\":\"2015-09-03\",\"workmk\":\"2\",\"worknm\":\"假日\",\"week_1\":\"4\",\"week_2\":\"星期四\",\"week_3\":\"周四\",\"week_4\":\"Thursday\",\"remark\":\"纪念日：世界反法西斯战争胜利70周年纪念日.\"}}";
		
		String encryptedMsg = aesEncrypt("abc123def456ghi7", message);
		System.out.println(encryptedMsg + "-" + encryptedMsg.length());
		String originalMsg = aesDecrypt("abc123def456ghi7", encryptedMsg);
		System.out.println(originalMsg + "-" + originalMsg.length());
	}

}
