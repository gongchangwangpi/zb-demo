package com.test;

import com.auth0.jwt.JWTSigner;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by books on 2017/8/24.
 */
public class JWTTest {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        String secret_test = "platform_secret";
        String secret_prod = "4a825d0d219744f0aab6875952e3662e";

        Map<String, Object> map = new TreeMap<>();
        map.put("mobile", "15680413834"); // 18780297073 15680413834
        map.put("source", "app");
//        map.put("source", "phoneSale");
//        map.put("source", "SELF");
        
        String sign = new JWTSigner(secret_test).sign(map);
//        String sign = new JWTSigner(secret_prod).sign(map);
        
        System.out.println(sign);

        System.out.println(System.currentTimeMillis() - start);
    }
    
}
