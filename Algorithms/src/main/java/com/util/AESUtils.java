package com.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * AES 加密/解密工具类
 * 
 * Created by books on 2017/12/28.
 */
public class AESUtils {

    /**
     * 加密
     * 
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey)throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[]raw = sKey.getBytes("utf-8");
        //			KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //	        System.out.println(sKey.getBytes().length);
        //          kgen.init(128, new SecureRandom(sKey.getBytes()));
        //          SecretKey secretKey = kgen.generateKey();
        //          byte[] raw = secretKey.getEncoded();

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[]encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        
        return DatatypeConverter.printBase64Binary(encrypted);
        
        //return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * 解密
     * 
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey)throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[]raw = sKey.getBytes("utf-8");
            //		        KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //	            kgen.init(128, new SecureRandom(sKey.getBytes()));
            //	            SecretKey secretKey = kgen.generateKey();
            //	            byte[] raw = secretKey.getEncoded();

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[]encrypted1 = DatatypeConverter.parseBase64Binary(sSrc); //先用base64解密
            //byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[]original = cipher.doFinal(encrypted1);
                return new String(original, "utf-8");
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}
