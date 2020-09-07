package com.test;

import java.util.Arrays;
import java.util.Random;

/**
 * 拼手气红包算法
 *
 * Created by books on 2017/12/4.
 */
public class HongBaoTest2 {

    public static void main(String[] args) {

        int totalMoney = 20000;
        int count = 30;

        for (int i = 0; i < 100; i++) {
            int[] redPacket = allocate(totalMoney, count);
            System.out.println(Arrays.toString(redPacket));

            validate(redPacket, totalMoney);
        }
    }

    public static void validate(int[] redPacket, int totalMoney) {
        int sum = 0;
        for (int i = 0; i < redPacket.length; i++) {
            int money = redPacket[i];
            if (money <= 0) {
                throw new IllegalArgumentException("红包分配错误，第" + (i + 1) + "个红包金额为：" + money);
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
            throw new IllegalArgumentException("money < 1");
        }

        if (count < 1) {
            throw new IllegalArgumentException("count < 1");
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
        Random random = new Random();
        for (int i = 0; i < count - 1; i++) {

            int currentRandom = random.nextInt(max - 1) + 1;

            int currentRemainTemp = remain - currentRandom;
            // 每次取随机数和剩余金额的最小值为当前红包金额
            int current = Math.min(currentRandom, currentRemainTemp);
            // 设置红包金额
            res[i] = current;
            // 重新计算剩余的钱
            remain -= current;

            if (i == count - 2) {
                // 循环到最后两次时，直接计算最后一个红包的金额，不在继续生成随机数
                res[count - 1] = remain;
                break;
            }

            // 重新计算剩余可分配的最大金额
            max = getMax(remain, count - i - 1);
        }

        return res;
    }

    /**
     * 单个红包的最大金额
     * 限制为平均数的X倍与剩余金额的最小值
     *
     * @param remain
     * @param count
     * @return
     */
    private static int getMax(int remain, int count) {
        int max = remain - count;
        int avg = remain / count;
        return Math.min(max, avg * 3);
    }
}
