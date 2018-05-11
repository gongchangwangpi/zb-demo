package com.spring.domain;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by books on 2018/1/2.
 */
public class Student {
    
    private Long id;
    
    @NotEmpty(message = "学生姓名不能为空")
    private String name;
    
    @Min(value = 0, message = "年龄必须大于0岁")
    @Max(value = 150, message = "年龄必须小于150岁")
    private Integer age;
    
    @NotEmpty(message = "学号不能为空")
    private String accountNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Student() {
    }

    public Student(Long id, @NotEmpty String name, Integer age, @NotEmpty String accountNo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.accountNo = accountNo;
    }
}
