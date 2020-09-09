package com.mybatis.test.user;

import java.util.List;

/**
 * @author zhangbo
 * @date 2020/9/7
 */
public interface MybatisUserMapper {

    int batchInsert(List<MybatisUserEntity> list);

    int updateById(MybatisUserEntity userEntity);

}
