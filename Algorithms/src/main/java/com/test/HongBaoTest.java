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

        int[] cal = cal(500, 1);
        System.out.println(Arrays.toString(cal));

        int sum = 0;
        for (int i = 0, len = cal.length; i < len; i++) {
            sum += cal[i];
        }
        System.out.println(sum);
    }

    /**
     * 计算手气红包
     * 
     * @param money 红包总金额：分
     * @param count 红包个数
     * @return 每个红包的金额
     */
    public static int[] cal(int money, int count) {
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
        
        int max = money - count;

        // 剩下的钱
        int remain = money;
        // 倒数第二个，便于计算最后一个红包(最后一个直接减，不用再随机数)
        for (int i = count - 1; i >= 0; i--) {
            int i1 = new Random().nextInt(max-1) + 1;
            int i2 = max - i1;
            // 每次取随机数和剩余的最小值
            int r = i1 < i2 ? i1 : i2; 
            // 设置红包金额
            res[i] = r;
            // 重新计算剩余的钱
            remain = remain - r;
            // 重新计算剩余可分配的最大金额
            max = remain - i + 1;
            
            if (i == 1) {
                // 循环到最后两次时，直接计算最后一个红包的金额，不在继续生成随机数
                res[0] = remain;
                break;
            }
            
            /*if (max < 1) {
                throw new IllegalArgumentException("max");
            }
            if (remain < 1) {
                throw new IllegalArgumentException("remain");
            }
            if (r < 1) {
                throw new IllegalArgumentException("r");
            }*/
        }
        
        return res;
    }
    
}
