package com.db.lock;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.db.lock.entity.DistributionLockEntity;
import com.db.lock.mapper.DistributionLockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2020/12/31
 */
@Slf4j
@Component
public class DistributionLockUtil {

    @Autowired
    private DistributionLockMapper distributionLockMapper;

    public boolean tryLock(DistributionLockNameEnum lockNameEnum, String lockValue, long lockTimeout, TimeUnit timeUnit) throws InterruptedException {
        Validate.notNull(lockNameEnum, "lock name is null");
        Validate.notBlank(lockValue, "lock value is blank");
        Validate.isTrue(lockTimeout > 0, "lockTimeout greater than 0");
        Validate.notNull(timeUnit, "time unit is null");

        log.info(" ===== begin lock, lockName = {}, lockValue = {}, lockTimeout = {}ms", lockNameEnum, lockValue, timeUnit.toMillis(lockTimeout));

        DistributionLockEntity existLock = getDistributionLockEntity(lockNameEnum);
        if (existLock == null) {
            DistributionLockEntity entity = new DistributionLockEntity();
            entity.setLockName(lockNameEnum.name());
            entity.setLockValue(lockValue);
            entity.setLockTime(LocalDateTime.now());
            entity.setTimeout(LocalDateTime.now().plusNanos(timeUnit.toNanos(lockTimeout)));
            try {
                distributionLockMapper.insert(entity);
                return true;
            } catch (DuplicateKeyException e) {
                // 插入失败，说明有其它线程加锁成功了
                return false;
            }
        } else {
            if (lockValue.equals(existLock.getLockValue())) {
                // 可重入
                existLock.setCount(existLock.getCount() + 1);
                existLock.setTimeout(LocalDateTime.now().plusNanos(timeUnit.toNanos(lockTimeout)));
                update(existLock, lockNameEnum, lockValue);
                return true;
            }

            // 其它线程持有锁，判断超时时间
            if (existLock.getTimeout().isBefore(LocalDateTime.now())) {
                // 超时，update
                DistributionLockEntity updateEntity = new DistributionLockEntity();
                updateEntity.setCount(1);
                updateEntity.setLockValue(lockValue);
                updateEntity.setLockTime(LocalDateTime.now());
                updateEntity.setTimeout(LocalDateTime.now().plusNanos(timeUnit.toNanos(lockTimeout)));
                int update = update(updateEntity, lockNameEnum, existLock.getLockValue());
                return update > 0;
            }
        }
        return false;
    }

    public boolean unlock(DistributionLockNameEnum lockNameEnum, String lockValue) {
        Validate.notNull(lockNameEnum, "lock name is null");
        Validate.notBlank(lockValue, "lock value is blank");

        log.info(" ===== unlock, lockName = {}, lockValue = {}", lockNameEnum, lockValue);

        DistributionLockEntity existLock = getDistributionLockEntity(lockNameEnum);
        if (existLock == null) {
            return true;
        }
        if (!lockValue.equals(existLock.getLockValue())) {
            throw new IllegalMonitorStateException("unlock other lock error");
        }
        if (existLock.getCount() - 1 == 0) {
            int delete = distributionLockMapper.delete(new QueryWrapper<DistributionLockEntity>().lambda()
                    .eq(DistributionLockEntity::getLockName, lockNameEnum)
                    .eq(DistributionLockEntity::getLockValue, lockValue));
            return delete > 0;
        } else {
            existLock.setCount(existLock.getCount() - 1);
            int update = update(existLock, lockNameEnum, lockValue);
            return update > 0;
        }
    }

    private DistributionLockEntity getDistributionLockEntity(DistributionLockNameEnum lockNameEnum) {
        return distributionLockMapper.selectOne(new QueryWrapper<DistributionLockEntity>().lambda()
                .eq(DistributionLockEntity::getLockName, lockNameEnum));
    }

    private int update(DistributionLockEntity updateEntity, DistributionLockNameEnum lockNameEnum, String lockValue) {
        return distributionLockMapper.update(updateEntity, new LambdaUpdateWrapper<DistributionLockEntity>()
                .eq(DistributionLockEntity::getLockName, lockNameEnum)
                .eq(DistributionLockEntity::getLockValue, lockValue));
    }
}
