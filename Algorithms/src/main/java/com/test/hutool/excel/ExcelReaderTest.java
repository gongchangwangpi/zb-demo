package com.test.hutool.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/7/21
 */
@Slf4j
public class ExcelReaderTest {

    public static void main(String[] args) {

        File file = new File("D:\\模板.xlsx");
        ExcelUtil.readBySax(file, 0, (sheetIndex, rowIndex, rowList) -> {
            System.out.println(rowList);
        });

        ExcelReader excelReader = ExcelUtil.getReader(file);
        List<Map<String, Object>> mapList = excelReader.readAll();
        System.out.println(mapList);

        ExcelReader excelReader1 = ExcelUtil.getReader(file);
        List<Order> mapList1 = excelReader1.readAll(Order.class);
        System.out.println(mapList1);

    }

    /**
     * 字段名称与表头对应
     */
    @Data
    static class Order {
        private String 卡券抵扣;
        private String 新零售交易类型;
        private String 订单编号;
        private String 买家会员名;
        private String 买家支付宝账号;
        private String 支付单号;
    }
}
