package com.zb.fund.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zb.fund.enums.GenderEnum;
import com.zb.fund.enums.JobEnum;
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
@TableName("t_man_user")
public class ManUserPlus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理主键
     */
    @TableId(type = IdType.AUTO)
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
