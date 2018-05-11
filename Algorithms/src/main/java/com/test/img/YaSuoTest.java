package com.test.img;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by books on 2017/6/7.
 */
public class YaSuoTest {

    public static void main(String[] args) throws IOException {

        File inFile = new File("F:/img/12.jpg");
        File outFile = new File("F:/img/122.jpg");

//        Thumbnails.of(inFile).scale(1f).outputQuality(0.5f).toFile(outFile);
        File temp = new File("tmp");
        InputStream inputStream = new FileInputStream(inFile);
        Thumbnails.of(inputStream).scale(1f).outputQuality(0.5f).toFile(temp);
    }

}
