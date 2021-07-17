package com.test.json;

import java.time.LocalDateTime;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
public interface DateConstant {

    /**
     * 数据库默认时间 1970-01-01 00:00:00
     */
    LocalDateTime DB_DEFAULT_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

}
