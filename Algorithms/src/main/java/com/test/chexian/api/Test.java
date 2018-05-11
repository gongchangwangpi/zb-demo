package com.test.chexian.api;

import com.test.chexian.api.encrypt.SymmetricEncryptionUtils;

/**
 * @author books
 */
public class Test {

    public static void main(String[] args) {

//        String key = "1234567890abcdef";
        String key = "$#56!38aa83!65#$";

        String str = "x0x5VK8QTByPz2ioQM4LPx6Ahfuf52tiLokLFco5yj8VS1UEwS4oCEDqUDtNfQ+A";

        String decrypt = SymmetricEncryptionUtils.aesDecrypt(key, str);

        System.out.println(decrypt);

    }
    
    
}
