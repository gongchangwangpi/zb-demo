package com.jdksource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author zhangbo
 */
public class LoadClassPathResources {

    public static void main(String[] args) throws IOException {
        loadFile("mappers/", "UserMapper.xml");
    }
 
    private static void loadFile(String dir, String fileName) throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = classLoader.getResources(dir + fileName);
        
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            readResource(url);
        }
    }
    
    private static void readResource(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
        
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    
}
