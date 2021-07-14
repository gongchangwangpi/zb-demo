package com.util.file;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author bo6.zhang
 * @date 2021/4/25
 */
public class ImageToPdfUtil {

    public static void toPdf(String imageFolderPath, String pdfPath) {
        // 获取图片文件夹对象
        File imageFolder = new File(imageFolderPath);
        File[] files = imageFolder.listFiles();

        mergePdf(pdfPath, files);
    }

    public static void mergePdf(String pdfPath, File[] files) {
        try {
            // 图片地址
            String imagePath = null;
            // 输入流
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img = null;
            // 实例化图片
            Image image = null;
            // 循环获取图片文件夹内的图片
            for (File file : files) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")
                        || file.getName().endsWith(".jpeg") || file.getName().endsWith(".tif")) {
                    // System.out.println(file1.getName());
                    imagePath = file.getAbsolutePath();
                    System.out.println(file.getName());
                    // 读取图片流
                    img = ImageIO.read(file);
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.newPage();
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("merge pdf success...");
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        toPdf("D:\\tmp\\", "D:\\books\\whoNeedsArchitect-Martin Flower-cn.pdf");
        long time2 = System.currentTimeMillis();
        int time = (int) ((time2 - time1) / 1000);
        System.out.println("执行了：" + time + "秒！");
    }

}
