package com.zookeeper.mastervote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户中心服务
 * 
 * @author zhangbo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterService implements Serializable {
    private static final long serialVersionUID = -2995858279634651358L;
    private Integer id;
    private String name;
}
