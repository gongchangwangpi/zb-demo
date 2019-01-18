package com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

/**
 * @author books
 */
public class Test2 {

    public static void main(String[] args) throws Exception {
        
        String imgPath = "https://img1.360buyimg.com/da/jfs/t17734/276/270845205/88073/727ff085/5a6862e2N2720507a.jpg";
//        String imgPath = "https://www.baidu.com";
//
        long s1 = System.currentTimeMillis();
//
        URL url = new URL(imgPath);
//        InputStream inputStream = url.openStream();
        
        InputStream inputStream = new FileInputStream("F:\\img\\pic");

        long s2 = System.currentTimeMillis();


//        boolean image = InputStreamUtil.isImage(inputStream);
//
//        System.out.println(image);
        
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();

        BufferedImage bufferedImage = ImageIO.read(inputStream);
//        BufferedImage bufferedImage = ImageIO.read(url);

        System.out.println("------ImageIO: " + (bufferedImage != null));
        
        System.out.println(Arrays.toString(readerFileSuffixes));

//        inputStream = url.openStream();


//        FileOutputStream outputStream = new FileOutputStream("F://img//1234321.jpg");
//
//        IOUtils.copy(inputStream, outputStream);

        System.out.println(s2 - s1);
        
    }
    
}
