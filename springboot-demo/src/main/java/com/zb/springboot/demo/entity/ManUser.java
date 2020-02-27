package com.zb.springboot.demo.entity;

import com.zb.springboot.demo.enums.GenderEnum;
import com.zb.springboot.demo.enums.JobEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
//@TableName("t_man_user")
public class ManUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理主键
     */
//    @TableId(type = IdType.NONE)
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
    private GenderEnum gender;

    /**
     * 职业
     */
    private JobEnum job;


}
