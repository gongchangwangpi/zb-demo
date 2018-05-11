package com.test.chexian.api.encrypt;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 消息摘要工具类
 * 
 * @author Edison Lyu
 *
 */
public final class MessageDigestUtils {

	private MessageDigestUtils() {

	}

	public static String md5DigestAsHex(String message) {
		MessageDigest messageDigest = getDigest("MD5");
		byte[] digest = messageDigest.digest(message.getBytes());
		return Hex.encodeHexString(digest);
	}
	
	public static String sha256Digest(String message, String charset) {
		MessageDigest messageDigest = getDigest("SHA-256");

		try {
			messageDigest.update(message.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not find Charset with name \"" + charset + "\"", e);
		}
        byte[] digest = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
	}

	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Could not find MessageDigest with algorithm \""+ algorithm + "\"", e);
		}
	}

}
