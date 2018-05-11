package com.zb.tcc.mapper.order;

import com.zb.tcc.domain.order.Order;

/**
 * @author zhangbo
 */
public interface OrderMapper {
    
    int insert(Order order);
    
    int update(Order order);

    Order select(Long id);

    Order select(String orderCode);
    
}
