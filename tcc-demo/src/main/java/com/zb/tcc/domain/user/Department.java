package com.zb.tcc.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class Department implements Serializable {
    
    private static final long serialVersionUID = -3772657471704145721L;

    @NotEmpty(message = "部门名称不能为空")
    @Length(min = 2, max = 20, message = "部门名称长度为[2-20]")
    private String departmentName;
    
    @NotEmpty(message = "部门人员不能为空")
    @Valid
    private List<User> users;

    public static void main(String[] args) throws JsonProcessingException {

        Department department = new Department();
        department.setDepartmentName("人事");

        List<User> users = new ArrayList<>();
        users.add(new User(1L, "zhangsan", 17));
        department.setUsers(users);

        System.out.println(new ObjectMapper().writeValueAsString(department));
    }
}
