package com.zb.fund.domain;

import java.io.Serializable;

import com.zb.fund.enums.GenderEnum;
import com.zb.fund.enums.JobEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别M F
     */
    private Integer gender;

    /**
     * 职业
     */
    private Integer job;


}
