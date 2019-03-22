package com.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class RsaUtil {
    
    //用于封装随机产生的公钥与私钥
    private static Map<Integer, String> keyMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
//        genKeyPair();
        
        keyMap.put(0, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPt7LrMHyycsaHC9cNmZ0Eur7M+86cst3Z8CK6Ko6NIlrkAzeVNXCGIZSFGKfTeCR7wHLOkFVU6+vgUp96GlcN2dbEk8crbhz38TkmRUIeKnSnNQw1GxYM2SiFzxeaUSfi2JBIrvUK2n990A2SDi3PfEy0O+KXPrvV5OWct5WhCQIDAQAB");
        keyMap.put(1, "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+3suswfLJyxocL1w2ZnQS6vsz7zpyy3dnwIroqjo0iWuQDN5U1cIYhlIUYp9N4JHvAcs6QVVTr6+BSn3oaVw3Z1sSTxytuHPfxOSZFQh4qdKc1DDUbFgzZKIXPF5pRJ+LYkEiu9Qraf33QDZIOLc98TLQ74pc+u9Xk5Zy3laEJAgMBAAECgYAqHXXj7+iKzpGY34JcrOhTi3oATcYlK1FR3Un/7hqqByhFax/trKRV2h9QxYHguXNPhHzgRXGMGjqcGPo86N0bdDW5bfVBKAoy0jCb050lc36YQ4hfHToTl5LdEXLFpxgQwTco6yPvBN50nhP+9V1a8S2b4CnfwXzOKPWQG5z9AQJBAM+peVcvK4vflvqjDSOdA4bGsJc6HEy94pd+Fjhf9bpsIm32dnkKd5k37ehhrZsAnrEAcej2vntyo/q5otJA0GkCQQCxK8rcbEoWUJbFTNLd471+vewjNENA8E0a/WEKrQtZufRAo7ycu0Px6D9D+Pp+ISZPS9GHl0UTk9jOyFkOLDehAkBzRkSRXSHzN178Kt70UgjPHCSTDjL/drj+F4QGL16c6cIDALmrX2AcsatjQDW6wZFV+EVDaRU8OdtFJx93T3thAkA3P7XKGQbdgJITwso7IarVHDrIG6MhXA5fbUZWwvG4MFHn2meV5Jvikw8vjjPnI0VEExurEdQbr847ZaTH06ghAkA1jRa1uc1AfLf3yindnMfE3SpvpvOMl6bRR1ACcbMUH70O2cyzBCxFFts1n/JLQA3Yez9fEyGPxfSKa/ZPFdkx");
        
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message, keyMap.get(0));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, keyMap.get(1));
        System.out.println("还原后的字符串为:" + messageDe);
        System.out.println("E0qTP6wqqqEUGsR4MvrujXdyq7Y1jQocBhaolQxCv9v/JCQwf4088X5kl4Wx5IQb9E5FnkNBSSk+6rpSTcQPva6qHcwSMPvzlh8G9eecBWlBDGd+sWrqedpYyBLtfDf2FUElfO1EfGD97ZiXUUKyyXZFOHCju69mqMC08ZDesoA=");
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串  
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
