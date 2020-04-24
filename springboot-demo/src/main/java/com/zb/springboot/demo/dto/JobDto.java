package com.zb.springboot.demo.dto;

import lombok.Data;

/**
 * 定时任务DTO
 *
 * @author zhangbo
 * @date 2020/4/24
 */
@Data
public class JobDto {

    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;

    private String jobDescription;

}
