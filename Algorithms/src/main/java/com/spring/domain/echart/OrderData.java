package com.spring.domain.echart;

import java.util.List;

/**
 * echarts 的订单数据
 * 
 * Created by books on 2017/10/27.
 */
public class OrderData {
    
    private List<String> xaxis;
    
    private List<Integer> quoteCount;
    
    private List<Integer> createCount;
    
    private List<Integer> payCount;

    public List<String> getXaxis() {
        return xaxis;
    }

    public void setXaxis(List<String> xaxis) {
        this.xaxis = xaxis;
    }

    public List<Integer> getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(List<Integer> quoteCount) {
        this.quoteCount = quoteCount;
    }

    public List<Integer> getCreateCount() {
        return createCount;
    }

    public void setCreateCount(List<Integer> createCount) {
        this.createCount = createCount;
    }

    public List<Integer> getPayCount() {
        return payCount;
    }

    public void setPayCount(List<Integer> payCount) {
        this.payCount = payCount;
    }
}
