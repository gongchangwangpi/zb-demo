package com.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * POI 处理Excel工具类
 * 
 * Created by books on 2017/12/1.
 */
public class PoiUtil {

    private PoiUtil() {
    }

    /**
     * 解析Excel单元格的字符串，忽略单元格格式
     * 
     * @param cell
     * @return
     */
    public static String getStringIgnoreCellType(Cell cell) {
        if (cell == null) {
            return null;
        }

        String cellValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                cell.setCellType(CellType.STRING);
                cellValue = cell.getStringCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case FORMULA:
                cell.setCellType(CellType.STRING);
                cellValue = cell.getStringCellValue();
                break;
            default:
                cellValue = null;
        }

        return StringUtils.isEmpty(cellValue) ? null : cellValue.trim();
    }

    /**
     * 解析时间
     * <p>
     *     解析Excel格式为时间的单元格或者格式为文本的时间字符串
     * </p>
     * 
     * @param cell
     * @param pattern
     * @return
     */
    public static Date getDate(Cell cell, String pattern) {
        if (cell == null) {
            return null;
        }

        Date cellValue = null;
        // 判断当前Cell的Type
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                }
                break;
            case STRING:
                String stringCellValue = cell.getStringCellValue();
                cellValue = com.util.DateUtil.parse(stringCellValue, pattern);
                break;
            default:
                cellValue = null;
        }

        return cellValue;
    }
    
    public static <T> List<T> parse(InputStream is, Class<T> clz) {
        if (is == null) {
            return null;
        }

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            try {
                workbook = new HSSFWorkbook(is);
            } catch (IOException e1) {
                throw new IllegalArgumentException("InputStream is not a Excel or version is not support");
            }
        }

        Sheet sheet = workbook.getSheetAt(0);
        List<T> list = new ArrayList<>();

        Field[] fields = clz.getFields();
        
        int i = 0;
        Row row = null;
        while ((row = sheet.getRow(i)) != null) {
            T instance = null;
            try {
                instance = clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            

            for (Field field : fields) {
                try {
                    field.set(instance, "");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            i++;
        }


        return list;
    }
}
