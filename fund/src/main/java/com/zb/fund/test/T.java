package com.zb.fund.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangbo
 */
public class T {

    public static void main(String[] args) throws IOException {

        try (FileInputStream inputStream = new FileInputStream("F://test.txt")) {

            String response = IOUtils.toString(inputStream);

            response = response.substring(response.indexOf("["));
            response = response.substring(0, response.indexOf("]") + 1);

            List<String> datas = JSON.parseArray(response, String.class);

            System.out.println(datas.size());

        }

    }
    
}
