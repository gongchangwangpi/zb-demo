package com.zb.tcc.domain.order;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class Order implements Serializable {
    
    private static final long serialVersionUID = -6076540576811580404L;
    
    private Long id;
    
    private String orderCode;
    
}
