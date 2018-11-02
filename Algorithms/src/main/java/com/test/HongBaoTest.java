package com.test;

import java.util.Arrays;
import java.util.Random;

/**
 * 拼手气红包算法
 * 
 * Created by books on 2017/12/4.
 */
public class HongBaoTest {

    public static void main(String[] args) {

        int totalMoney = 500;
        int count = 100;

        for (int i = 0; i < 100; i++) {
            int[] redPacket = allocate(totalMoney, count);
            System.out.println(Arrays.toString(redPacket));

            validate(redPacket, totalMoney);
        }
    }
    
    private static void validate(int[] redPacket, int totalMoney) {
        int sum = 0;
        for (int i = 0; i < redPacket.length; i++) {
            int money = redPacket[i];
            if (money <= 0) {
                throw new IllegalArgumentException("红包分配错误，第" + i + "个红包金额为：" + money);
            }
            sum += money;
        }
        if (sum != totalMoney) {
            throw new IllegalArgumentException("红包分配错误，总金额不正确。应分配总金额为：" + totalMoney + "，实际分配总金额为：" + sum);
        }
    }

    /**
     * 计算手气红包
     * 
     * @param money 红包总金额：分
     * @param count 红包个数
     * @return 每个红包的金额
     */
    public static int[] allocate(int money, int count) {
        if (money < 1) {
            throw new IllegalArgumentException("money");
        }

        if (count < 1) {
            throw new IllegalArgumentException("count");
        }
        
        if (count > money) {
            throw new IllegalArgumentException("count > money");
        }
        
        int[] res = new int[count];
        
        if (count == 1) {
            res[0] = money;
            return res;
        }
        if (count == money) {
            Arrays.fill(res, 1);
            return res;
        }
        // 剩下的钱
        int remain = money;
        // 理论上单个红包的最大金额
        int max = getMax(remain, count - 1);

        // 倒数第二个，便于计算最后一个红包(最后一个直接减，不用再随机数)
        for (int i = count - 1; i >= 0; i--) {
            
            int currentRandom = new Random().nextInt(max - 1) + 1;
            
            int currentRemainTemp = remain - currentRandom;
            // 每次取随机数和剩余的最小值为当前红包金额
            int current = currentRandom < currentRemainTemp ? currentRandom : currentRemainTemp; 
            // 设置红包金额
            res[i] = current;
            // 重新计算剩余的钱
            remain -= current;

            if (i == 1) {
                // 循环到最后两次时，直接计算最后一个红包的金额，不在继续生成随机数
                res[0] = remain;
                break;
            }

            // 重新计算剩余可分配的最大金额
            max = getMax(remain, i - 1);
        }
        
        return res;
    }

    /**
     * 单个红包的最大金额
     * 限制为平均数的10倍与剩余金额的最小值
     * 
     * @param remain
     * @param count
     * @return
     */
    private static int getMax(int remain, int count) {
        int max = remain - count;
        int avg = remain / count;
        return max > avg * 10 ? avg * 10 : max;
    }
}
