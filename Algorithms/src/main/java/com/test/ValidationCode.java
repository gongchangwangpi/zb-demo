package com.test;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @author zhangbo
 */
public class ValidationCode {


    /**
     * 验证码图片的宽度
     */
    private int width = 76;

    /**
     * 验证码图片的高度
     */
    private int height = 34;

    /**
     * 验证码字符个数
     */
    private int codeCount = 4;

    /**
     * xx
     */
    private int xx = width / (codeCount + 2); //生成随机数的水平距离

    /**
     * 字体高度
     */
    private int fontHeight = height - 12;   //生成随机数的数字高度

    /**
     * codeY
     */
    private int codeY = height - 8;      //生成随机数的垂直距离

    /**
     * codeSequence
     */
    private String[] codeSequence = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "Q", "W", "E", "R", "T", "Y", "U", "I", "P", "A", "S", "D", "F", "G",
            "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};


    public void verificationCode() throws ServletException, IOException {

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体
        gd.setFont(font);

        // 画边框
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生4条干扰线，使图象中的认证码不易被其它程序探测到
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证
        StringBuilder randomCode = new StringBuilder();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字
            String strRand = String.valueOf(codeSequence[random.nextInt(27)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
            red = random.nextInt(125);
            green = random.nextInt(255);
            blue = random.nextInt(200);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(strRand, (i + 1) * xx, codeY);

            // 将产生的四个随机数组合在一起
            randomCode.append(strRand);
        }

        System.out.println(randomCode);
        
        // 将验证码保存到Session中
//        session.setAttribute(SessionConstants.VERIFICATION_CODE, randomCode.toString());

        // 禁止图像缓存
//        resp.setHeader("Pragma", "no-cache");
//        resp.setHeader("Cache-Control", "no-cache");
//        resp.setDateHeader("Expires", 0);
//
//        resp.setContentType("image/jpeg");
//
//        // 将图像输出到Servlet输出流中
//        ServletOutputStream sos = resp.getOutputStream();
//        ImageIO.write(buffImg, "jpeg", sos);
//        sos.close();

        // 转Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "jpg", baos);
        Base64.getEncoder().encodeToString(baos.toByteArray());
        
    }
    
}
