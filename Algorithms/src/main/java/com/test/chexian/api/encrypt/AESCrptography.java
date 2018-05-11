package com.test.chexian.api.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class AESCrptography {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String content = "{\"success\":\"1\",\"result\":{\"date\":\"2015-09-03\",\"workmk\":\"2\",\"worknm\":\"假日\",\"week_1\":\"4\",\"week_2\":\"星期四\",\"week_3\":\"周四\",\"week_4\":\"Thursday\",\"remark\":\"纪念日：世界反法西斯战争胜利70周年纪念日.\"}}";
		String key = "abcdabcdefghefgh";
		String iv = "abcdefghijklmnop";

		System.out.println("加密前：" + byteToHexString(content.getBytes()));
		byte[] encrypted = AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes());
		System.out.println("加密后：" + byteToHexString(encrypted));
		byte[] decrypted = AES_CBC_Decrypt(encrypted, key.getBytes(), iv.getBytes());
		System.out.println("解密后：" + new String(decrypted));
		System.out.println("解密后：" + byteToHexString(decrypted));
	}

	public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv) {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv) {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			// key的长度可设为128,192,256位，这里只能设为128
			keyGenerator.init(128, new SecureRandom(keyBytes));
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
}
