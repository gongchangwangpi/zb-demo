package com.jdksource.util;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author zhangbo
 */
public class PropertiesTest {

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        properties.setProperty("key1", "value1");
        properties.setProperty("key2", "value2");
        properties.setProperty("key3", "value3");

        FileOutputStream outputStream = new FileOutputStream("properties.prop");

        properties.store(outputStream, "propertiesTest1");
        
        outputStream.close();
    }
    
}
