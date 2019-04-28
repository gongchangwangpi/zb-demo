package com.test.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbo
 */
public class EasyListener extends AnalysisEventListener {
    
    private List<BrokeragePolicy> data = new ArrayList<>();
    
    @Override
    public void invoke(Object object, AnalysisContext context) {
        data.add((BrokeragePolicy) object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<BrokeragePolicy> getData() {
        return data;
    }
}
