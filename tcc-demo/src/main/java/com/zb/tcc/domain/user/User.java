package com.zb.tcc.domain.user;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class User implements Serializable {
    
    private static final long serialVersionUID = 8673032277800075330L;
    
    @Min(value = 1, message = "id不能小于1")
    private Long id;
    
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度为[2-20]之间")
    private String username;

    @Range(min = 0, max = 150, message = "用户年龄为[0-150]之间")
    private Integer age;
    
}
