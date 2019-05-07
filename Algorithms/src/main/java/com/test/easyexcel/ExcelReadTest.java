package com.test.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.zb.demo.util.io.ConsoleUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * -Xms512M -Xmx512M -XX:+UseConcMarkSweepGC 
 * -XX:+PrintGCCause -XX:+PrintGCDetails -XX:+PrintGCDateStamps  -XX:+PrintGCTimeStamps 
 * -XX:+PrintHeapAtGC -Xloggc:/home/excel/gc.log 
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/excel/test.dump
 * 
 * -Xms512M -Xmx512M -XX:+PrintGCCause -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:/home/excel/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/excel/test.dump
 * 
 * @author zhangbo
 */
@Slf4j
public class ExcelReadTest {
    
    public static final String POI = "poi";
    public static final String EASY_EXCEL = "easyexcel";
    public static final String GC = "gc";
    public static final String REMAINS = "remains";
    public static final String SLEEP = "sleep";
    
    public static volatile String command = SLEEP;
    
    public static final String FILE_PATH = "F:\\佣金垫资\\佣金导入-金惠家.xlsx";
    public static final List<Object> remains = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        // 监听控制台输入
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("已启动");
                while (true) {
                    command = ConsoleUtil.read();
                }
            }
        }).start();
        
        // 读取Excel
        while (true) {
            
//            String command = ConsoleUtil.read();
            
            if (POI.equalsIgnoreCase(command)) {

                FileInputStream fis = new FileInputStream(FILE_PATH);
                
                System.out.println("===== POI read start =====");

                List<BrokeragePolicy> list = PoiUtil.read(fis);
                remainAny(list);

                System.out.println("POI read data: " + list.size() + ", Remains size: " + remains.size());
                System.out.println("===== POI read end =====");

                TimeUnit.SECONDS.sleep(3);

            } else if (EASY_EXCEL.equalsIgnoreCase(command)) {

                System.out.println("===== EASY EXCEL read start =====");

                FileInputStream fis = new FileInputStream(FILE_PATH);

                EasyListener excelListener = new EasyListener();
                EasyExcelFactory.readBySax(fis, new Sheet(1, 3, BrokeragePolicy.class), excelListener);

                List<BrokeragePolicy> list = excelListener.getData();
                remainAny(list);
                
                System.out.println("EASY EXCEL read data: " + list.size() + ", Remains size: " + remains.size());
                System.out.println("===== EASY EXCEL read end =====");

                TimeUnit.SECONDS.sleep(3);

            } else if (REMAINS.equalsIgnoreCase(command)) {

                System.out.println("===== REMAINS ===== size: " + remains.size());

            }  else if (GC.equalsIgnoreCase(command)) {

                System.out.println("===== GC start =====");
                System.gc();
                System.out.println("===== GC end =====");

            } else {
                System.out.println("===== sleep");
                while (SLEEP.equalsIgnoreCase(command)) {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        }
        
    }

    private static void remainAny(List<BrokeragePolicy> list) {
        int remainSize = random.nextInt(list.size() / 2) + list.size() / 2;
        remains.addAll(list.subList(0, remainSize));
    }

}
