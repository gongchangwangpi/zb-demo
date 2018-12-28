package com.test.excel;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jxls 单元格合并
 * 
 * transformer.transformXLS(is, beanMap) 调用之后，单元格显示的内容就已确定，如果在改变 beanMap 里面的值，最终的Excel的值也不会变
 * 
 * Created by books on 2017/10/17.
 */
public class JxlsTest {

    public static void main(String[] args) throws Exception {
        
        String sourceFilePath = "E:\\test\\carInsPolicyLoanApplyData.xls";
        String destFilePath = "E:\\test\\user1.xls";
        
        InputStream is = new FileInputStream(sourceFilePath);
        OutputStream os = new FileOutputStream(destFilePath);

        try {
            List<User> list = new ArrayList<>();
            list.add(new User(1L, "name1", "word1", "1", 300L));
            list.add(new User(2L, "name2", "word2", "1", 200L));
            list.add(new User(3L, "name3", "word3", "2", 300L));
            list.add(new User(4L, "name4", "word4", "3", 400L));
            list.add(new User(5L, "name5", "word5", "4", 1100L));
            list.add(new User(6L, "name6", "word6", "4", 600L));
            list.add(new User(6L, "name6", "word6", "4", 600L));
            list.add(new User(6L, "name6", "word6", "4", 600L));
            list.add(new User(6L, "name6", "word6", "5", 8000L));
            list.add(new User(6L, "name6", "word6", "5", 600L));
            list.add(new User(6L, "name6", "word6", "6", 600L));

            XLSTransformer transformer = new XLSTransformer();

            Map<String, Object> beanMap = new HashMap<>();
            beanMap.put("data", list);
            
            Workbook workbook = transformer.transformXLS(is, beanMap);
            Sheet sheet = workbook.getSheetAt(0);
            
            String lastSchoolId = null;
            int size = list.size();
            // 需要合并的单元格行数
            int cells = 1;
            
            for (int i = 0; i < size; i++) {
                User user = list.get(i);
                String schoolId = user.getSchoolId();
                if (StringUtils.isNotEmpty(schoolId)) {
                    
                    if (schoolId.equals(lastSchoolId)) {
                        // 和上一次相等,计算合并的单元格
                        cells++;

                    } else {
                        // 和上一次不等
                        lastSchoolId = schoolId;
                        if (cells > 1) {
                            // 设置合并单元格
                            sheet.addMergedRegion(new CellRangeAddress(i-(cells-1), i, 3, 3));
                            sheet.addMergedRegion(new CellRangeAddress(i-(cells-1), i, 4, 4));
                            // 清除合并标记
                            cells = 1;
                        }
                    }
                }
            }

            if (cells > 1) {
                // 设置合并单元格
                sheet.addMergedRegion(new CellRangeAddress(size-(cells-1), size, 3, 3));
                sheet.addMergedRegion(new CellRangeAddress(size-(cells-1), size, 4, 4));
            }

            workbook.write(os);
            
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }

    }
    
}
