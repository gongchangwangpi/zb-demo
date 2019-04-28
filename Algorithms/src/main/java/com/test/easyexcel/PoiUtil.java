package com.test.easyexcel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiUtil {
    
    public static final String DEFAULT_EXCEL_DATE_PATTERN = "yyyy/M/d";

    private PoiUtil() {
    }

    public static List<BrokeragePolicy> read(InputStream is) throws Exception {

        List<BrokeragePolicy> brokeragePolicyList;

        try {
            Workbook wb = WorkbookFactory.create(is);
            // 得到表对象
            Sheet sheet = wb.getSheetAt(0);
            // 从第4行开始读取数据
            int i = 3;
            Row row = null;

            brokeragePolicyList = new ArrayList<>();

            while ((row = sheet.getRow(i++)) != null) {
                BrokeragePolicy brokeragePolicy = new BrokeragePolicy();

                brokeragePolicy.setEnterpriseName(PoiUtil.getStringValue(row.getCell(0)));
                brokeragePolicy.setInsuranceCompanyName(PoiUtil.getStringValue(row.getCell(1)));
                brokeragePolicy.setChannel(PoiUtil.getStringValue(row.getCell(2)));
                brokeragePolicy.setCoverage(PoiUtil.getStringValue(row.getCell(3)));

                brokeragePolicy.setPolicyNo(PoiUtil.getStringValue(row.getCell(4)));
                brokeragePolicy.setEndorseNo(PoiUtil.getStringValue(row.getCell(5)));
                brokeragePolicy.setLicensePlate(PoiUtil.getStringValue(row.getCell(6)));
                brokeragePolicy.setPolicyHolder(PoiUtil.getStringValue(row.getCell(7)));
                brokeragePolicy.setPolicyInsured(PoiUtil.getStringValue(row.getCell(8)));
                brokeragePolicy.setBeginDate(PoiUtil.getDateValue(row.getCell(9), PoiUtil.DEFAULT_EXCEL_DATE_PATTERN));
                brokeragePolicy.setEndDate(PoiUtil.getDateValue(row.getCell(10), PoiUtil.DEFAULT_EXCEL_DATE_PATTERN));
                brokeragePolicy.setIssueDate(PoiUtil.getDateValue(row.getCell(11), PoiUtil.DEFAULT_EXCEL_DATE_PATTERN));

                brokeragePolicy.setPremiumAmount(PoiUtil.getDecimalValue(row.getCell(12)));
                brokeragePolicy.setVehicleVesselTax(PoiUtil.getDecimalValue(row.getCell(13)));
                brokeragePolicy.setTotalPremiumAmount(PoiUtil.getDecimalValue(row.getCell(14)));

                brokeragePolicy.setProcedureFeeRate(PoiUtil.getStringValue(row.getCell(15)));
                brokeragePolicy.setProcedureFeeAmount(PoiUtil.getDecimalValue(row.getCell(16)));
                brokeragePolicy.setTicketOutsideProcedureFeeOrgName(PoiUtil.getStringValue(row.getCell(17)));
                brokeragePolicy.setTicketOutsideProcedureFeeRate(PoiUtil.getStringValue(row.getCell(18)));
                brokeragePolicy.setTicketOutsideProcedureFeeAmount(PoiUtil.getDecimalValue(row.getCell(19)));

                brokeragePolicy.setAgentName(PoiUtil.getStringValue(row.getCell(20)));
                brokeragePolicy.setAgentIdNo(PoiUtil.getStringValue(row.getCell(21)));
                brokeragePolicy.setAgentBankCardNo(PoiUtil.getStringValue(row.getCell(22)));
                brokeragePolicy.setAgentBankMobile(PoiUtil.getStringValue(row.getCell(23)));
                brokeragePolicy.setBrokerageRate(PoiUtil.getStringValue(row.getCell(24)));
                brokeragePolicy.setBrokerageAmount(PoiUtil.getDecimalValue(row.getCell(25)));

                brokeragePolicyList.add(brokeragePolicy);
            }

        } finally {
            IOUtils.closeQuietly(is);
        }

        return brokeragePolicyList;
    }

    public static String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        String cellValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                // 设置为字符型，获取到的数值没有.0
//                cell.setCellType(CellType.STRING);
//                cellValue = cell.getStringCellValue();
                double value = cell.getNumericCellValue();
                cellValue = value + "";
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

        return StringUtils.trimToNull(cellValue);
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
    public static Date getDateValue(Cell cell, String pattern) {
        if (cell == null) {
            return null;
        }

        Date cellValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                }
                break;
            case STRING:
                String stringCellValue = cell.getStringCellValue();
                cellValue = parseDate(stringCellValue, pattern);
                break;
            default:
                cellValue = null;
        }

        return cellValue;
    }

    public static BigDecimal getDecimalValue(Cell cell) {
        String stringValue = getStringValue(cell);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        return getBigDecimal(stringValue);
    }

    public static BigDecimal getRateValue(Cell cell) {
        String stringValue = getStringValue(cell);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        if (stringValue.endsWith("%")) {
            stringValue = stringValue.substring(0, stringValue.length() - 1);
        } else {
            throw new IllegalArgumentException("比例格式不正确，须以'%'结尾：" + stringValue);
        }
        return getBigDecimal(stringValue);
    }

    private static BigDecimal getBigDecimal(String stringValue) {
        try {
            BigDecimal bigDecimal = new BigDecimal(stringValue);
            if (bigDecimal.scale() > 2) {
                throw new IllegalArgumentException();
            }
            return bigDecimal;
        } catch (Exception e) {
            throw new IllegalArgumentException("金额或比例格式不正确，最多包含两位小数：" + stringValue);
        }
    }
    
    private static Date parseDate(String value, String pattern) {
        try {
            if (StringUtils.isNotEmpty(value)) {
                return new SimpleDateFormat(pattern).parse(value);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("时间格式有误：" + value);
        }
        return null;
    }
}
