package com.zb.springboot.demo.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/3/5
 */
public interface TxMapper {

    @Select("show variables like 'tx_isolation'")
    Map<String, String> showSessionIsolation();

    @Select("show global variables like 'tx_isolation'")
    Map<String, String> showGlobalIsolation();

}
