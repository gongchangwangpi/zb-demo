package com.test.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/9/18
 */
public class EasyExcelTest {

    public static void main(String[] args) {
        File file = new File("D:\\Wxws\\CDP\\CDP2.1界面化\\淘宝订单模板(2)(1).xlsx");

        List<Object> objects = EasyExcel.read(file).sheet().doReadSync();

        System.out.println(objects);

        EasyExcel.read(file, new AnalysisEventListener() {

            @Override
            public void invokeHead(Map headMap, AnalysisContext context) {
                System.out.println(headMap);
            }

            @Override
            public void invoke(Object data, AnalysisContext context) {
                System.out.println(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }

        }).sheet().doRead();

    }

}
