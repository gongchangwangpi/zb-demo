package com.zb.springboot.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author zhangbo
 * @date 2019-11-29
 */
@Data
public class LocalDateTimeQueryReq implements Serializable {

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

}
