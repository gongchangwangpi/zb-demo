package com.test.any;

import cn.hutool.crypto.digest.MD5;

/**
 * @author bo6.zhang
 * @date 2021/2/20
 */
public class BitBlockChainTest {

    public static void main(String[] args) {

        // 区块链的hash值穷举

        // 需要计算的数据
        String data = "helloworld";

        // 计算hash的目标
        String target = "0000000";

        // 随机数
        int nonce = 0;

        long begin = System.currentTimeMillis();

        while (true) {
            String d = data + nonce;
            String digestHex = MD5.create().digestHex(d);
            if (digestHex.startsWith(target)) {
                break;
            }
            nonce++;
        }

        long end = System.currentTimeMillis();

        System.out.println("nonce = " + nonce);
        System.out.println("cost = " + (end - begin));

    }

}
