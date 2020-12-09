package com.test.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhangbo
 * @date 2020/4/24
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    private Integer id;
    private String userName;
    private Long classId;
    private String sex;
    private StudentStatusEnum status;

}
