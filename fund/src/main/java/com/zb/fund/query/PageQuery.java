package com.zb.fund.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class PageQuery {
    
    private int pageNum = 1;
    
    private int pageSize = 10;
    
    private String sortColumn = " id ";
    
    private String sortType = " ASC ";
    
}
