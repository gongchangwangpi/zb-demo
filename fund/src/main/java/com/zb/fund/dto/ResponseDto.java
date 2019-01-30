package com.zb.fund.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class ResponseDto {
    
    private List<String> datas;
    
    private Integer allRecords;
    private Integer pageIndex;
    private Integer pageNum;
    private Integer allPages;
    private Integer allNum;
    private Integer gpNum;
    private Integer hhNum;
    private Integer zqNum;
    private Integer zsNum;
    private Integer bbNum;
    private Integer qdiiNum;
    private Integer etfNum;
    private Integer lofNum;
    private Integer fofNum;
    
}
