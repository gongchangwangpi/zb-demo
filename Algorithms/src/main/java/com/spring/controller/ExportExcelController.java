package com.spring.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.test.chexian.api.dto.RestfulResultDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
@RestController
public class ExportExcelController {

    @GetMapping(value = "/export")
    public RestfulResultDto export(HttpServletResponse response) throws IOException {
        String fileName = "测试01ABC";
        fileName = org.apache.commons.lang3.StringUtils.defaultIfEmpty(fileName, "file");
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
//        writer.write(data, sheet);

        return RestfulResultDto.success();
    }

}
