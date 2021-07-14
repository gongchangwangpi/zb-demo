package com.zb.springboot.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定时任务DTO
 *
 * @author zhangbo
 * @date 2020/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;

    private String jobDescription;

    private Integer minutes;

}
