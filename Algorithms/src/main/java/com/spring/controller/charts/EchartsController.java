package com.spring.controller.charts;

import com.spring.domain.echart.OrderData;
import com.spring.domain.echart.OrderQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by books on 2017/10/27.
 */
@Controller
@RequestMapping(value = "/echarts")
public class EchartsController {
    
    @RequestMapping(value = "/getOrderData")
    @ResponseBody
    public Object getOrderData(OrderQuery query) {
        OrderData orderData = new OrderData();
        
        List<String> xAxis = new ArrayList<>();
        xAxis.add("1013");
        xAxis.add("1014");
        xAxis.add("1015");
        xAxis.add("1016");
        xAxis.add("1017");
        xAxis.add("1018");
        xAxis.add("1019");
        xAxis.add("1020");
        xAxis.add("1021");
        xAxis.add("1022");
        xAxis.add("1023");
        xAxis.add("1024");
        xAxis.add("1025");
        xAxis.add("1026");
        orderData.setXaxis(xAxis);
        
        List<Integer> createCount = new ArrayList<>();
        createCount.add(10);
        createCount.add(200);
        createCount.add(130);
        createCount.add(125);
        createCount.add(60);
        createCount.add(50);
        createCount.add(350);
        createCount.add(255);
        createCount.add(88);
        createCount.add(5);
        createCount.add(10);
        createCount.add(150);
        createCount.add(50);
        createCount.add(23);
        orderData.setCreateCount(createCount);
        
        List<Integer> payCount = new ArrayList<>();
        payCount.add(10);
        payCount.add(200);
        payCount.add(53);
        payCount.add(100);
        payCount.add(48);
        payCount.add(5);
        payCount.add(280);
        payCount.add(222);
        payCount.add(50);
        payCount.add(2);
        payCount.add(6);
        payCount.add(100);
        payCount.add(48);
        payCount.add(10);
        
        orderData.setPayCount(payCount);
        
        return orderData;
    }
    
}
