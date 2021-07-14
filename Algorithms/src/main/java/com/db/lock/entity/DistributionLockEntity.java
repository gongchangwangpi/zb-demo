package com.db.lock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangbo
 * @date 2020/12/31
 */
@Data
@TableName("distribution_lock")
public class DistributionLockEntity implements Serializable {

    /**
     * 锁名称，如订单
     */
    private String lockName;
    /**
     * 锁标识
     */
    private String lockValue;
    /**
     * 加锁次数
     */
    private Integer count;
    /**
     * 加锁时间
     */
    private LocalDateTime lockTime;
    /**
     * 超时时间
     */
    private LocalDateTime timeout;

}
