package com.test.img;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author bo6.zhang
 * @date 2021/4/1
 */
@Slf4j
@Component
public class PosterUtil {

    //文字显示
    /**
     * 二维码颜色:黑色
     */
    private static final int QRCOLOR = 0x201f1f;
    /**
     * 二维码背景颜色:白色
     */
    private static final int BGWHITE = 0xFFFFFF;

    /**
     * 设置QR二维码参数信息
     */
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;

        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置QR二维码的纠错级别(H为最高级别)
            put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
            put(EncodeHintType.MARGIN, 0);// 白边
        }
    };

    /**
     * 获取远程的图片
     *
     * @param imageUrl
     * @return
     */
    public BufferedImage getRemoteImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            try {
                return ImageIO.read(url);
            } catch (IOException e) {
                log.error("从图片服务器获取图片异常", e);
            }
        } catch (MalformedURLException me) {
            log.error("从图片服务器获取失败", me);
        }
        return null;
    }

    /**
     * 获取本地的图片
     *
     * @param filePath
     * @return
     */
    public BufferedImage getLocalFile(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            log.error("从本地获取图片异常", e);
        }
        return null;
    }

    public String createPosterUrl(String posterUrl, String qrcodeUrl,
                                  String avatarUrl, String nickname, PosterCoordinate posterCoordinate) throws IOException {
        InputStream inputStream = createPoster(getRemoteImage(posterUrl), getRemoteImage(qrcodeUrl), posterCoordinate.getQrcodeCoordinate(),
                getRemoteImage(avatarUrl), posterCoordinate.getAvatarCoordinate(),
                nickname, posterCoordinate.getNickNameCoordinate());
        return "";
    }


    public InputStream createPosterQrCode(BufferedImage bgImage, BufferedImage qrcodeImage, ImageCoordinate qrcodeCoordinate) {

        Graphics2D rng = bgImage.createGraphics();
        rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));

        // 写入二维码
        if (Objects.nonNull(qrcodeImage) && Objects.nonNull(qrcodeCoordinate)) {
            rng.drawImage(qrcodeImage, qrcodeCoordinate.getX(), qrcodeCoordinate.getY(), qrcodeCoordinate.getWidth(), qrcodeCoordinate.getHeight(), null);
        }
        rng.dispose();
        bgImage.flush();
        return bufferedImageToInputStream(bgImage);
    }

    public InputStream createPoster(String posterUrl, String qrcodeUrl,
                                    String avatarUrl, String nickname, PosterCoordinate posterCoordinate) {
        return createPoster(getRemoteImage(posterUrl), getRemoteImage(qrcodeUrl), posterCoordinate.getQrcodeCoordinate(),
                getRemoteImage(avatarUrl), posterCoordinate.getAvatarCoordinate(),
                nickname, posterCoordinate.getNickNameCoordinate());
    }

    public InputStream createPoster(BufferedImage bgImage, BufferedImage qrcodeImage, ImageCoordinate qrcodeCoordinate,
                                    BufferedImage avatarImage, ImageCoordinate avatarCoordinate,
                                    String nickname, StringCoordinate nicknameCoordinate) {

        Graphics2D rng = bgImage.createGraphics();
        rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
//        int strWidth = rng.getFontMetrics().stringWidth(nickname);
        // 写入昵称
        if (StringUtils.isNotEmpty(nickname) && Objects.nonNull(nicknameCoordinate)) {
            // 设置字体类型和大小(BOLD加粗/ PLAIN平常)
            rng.setFont(new Font("Serif", Font.BOLD, nicknameCoordinate.getSize()));
//            rng.setFont(getSupportChineseFont(nicknameCoordinate.getSize()));
            // 设置字体颜色
            rng.setColor(Color.black);
            rng.drawString(nickname, nicknameCoordinate.getX(), nicknameCoordinate.getY());
        }
        // 写入头像
        if (Objects.nonNull(avatarImage) && Objects.nonNull(avatarCoordinate)) {
            rng.drawImage(avatarImage, avatarCoordinate.getX(), avatarCoordinate.getY(), avatarCoordinate.getWidth(), avatarCoordinate.getHeight(), null);
        }
        // 写入二维码
        if (Objects.nonNull(qrcodeImage) && Objects.nonNull(qrcodeCoordinate)) {
            rng.drawImage(qrcodeImage, qrcodeCoordinate.getX(), qrcodeCoordinate.getY(), qrcodeCoordinate.getWidth(), qrcodeCoordinate.getHeight(), null);
        }
        rng.dispose();
        bgImage.flush();
        return bufferedImageToInputStream(bgImage);
    }

    private static Font getSupportChineseFont(int size) {
        String s = "同程⑴×123abc";
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : ge.getAllFonts()) {
            if (font.canDisplayUpTo(s) == -1) {
                return new Font(font.getFontName(), Font.BOLD, size);
            }
        }
        return new Font("Serif", Font.BOLD, size);
    }

    /**
     * 生成二维码图片+背景+文字描述
     *
     * @param backgroundImage 背景图地址
     * @param WIDTH           二维码宽度
     * @param HEIGHT          二维码高度
     * @param qrUrl           二维码识别地址
     * @param note            文字描述1
     * @param tui             文字描述2
     * @param size            文字大小
     * @param imagesX         二维码x轴方向
     * @param imagesY         二维码y轴方向
     * @param text1X          文字描述1x轴方向
     * @param text1Y          文字描述1y轴方向
     * @param text2X          文字描述2x轴方向
     * @param text2Y          文字描述2y轴方向
     */
    public InputStream createQRCode(BufferedImage backgroundImage, Integer WIDTH, Integer HEIGHT, String qrUrl,
                                    String note, String tui, Integer size, Integer imagesX, Integer imagesY, Integer text1X, Integer text1Y
            , Integer text2X, Integer text2Y) {
        try {
            BufferedImage image = generateQRCode(qrUrl, WIDTH, HEIGHT);
            /*
             *     添加背景图片
             */
            int bgWidth = backgroundImage.getWidth();
            //距离背景图片x边的距离，居中显示
            int disx = imagesX;
            //距离y边距离 * * * *
            int disy = imagesY;
            Graphics2D rng = backgroundImage.createGraphics();
            rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
            rng.drawImage(image, disx, disy, WIDTH, HEIGHT, null);

            /*
             *     文字描述参数设置
             */
            Color textColor = Color.white;
            rng.setColor(textColor);
            rng.drawImage(backgroundImage, 0, 0, null);
            //设置字体类型和大小(BOLD加粗/ PLAIN平常)
            rng.setFont(new Font("微软雅黑,Arial", Font.BOLD, size));
            //设置字体颜色
            rng.setColor(Color.black);
            int strWidth = rng.getFontMetrics().stringWidth(note);

            //文字1显示位置
            int disx1 = (bgWidth - strWidth) - text1X;//左右
            rng.drawString(note, disx1, text1Y);//上下

            //文字2显示位置
            int disx2 = (bgWidth - strWidth) - text2X;//左右
            rng.drawString(tui, disx2, text2Y);//上下

            rng.dispose();
            image = backgroundImage;
            image.flush();
            return bufferedImageToInputStream(image);
        } catch (Exception e) {
            log.error("生成二维码图片，上传服务器失败", e);
        }
        return null;
    }

    private BufferedImage generateQRCode(String qrParam, Integer WIDTH, Integer HEIGHT) throws WriterException {
        // 参数顺序分别为: 编码内容,编码类型,生成图片宽度,生成图片高度,设置参数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrParam, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

        //调用去除白边方法
        bitMatrix = deleteWhiteNew(bitMatrix,0);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        return image;
    }


    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    private static BitMatrix deleteWhiteNew(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }


    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            log.error("image to input stream fail", e);
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //
        PosterUtil util = new PosterUtil();

//        BufferedImage bg = util.getLocalFile("D:\\Images\\SB\\poster_bg.png");
        BufferedImage bg = util.getRemoteImage("https://pavo.elongstatic.com/i/ori/Z54uFxfjQA.png");

        BufferedImage avatar = util.getLocalFile("D:\\Images\\SB\\avatar.png");
        ImageCoordinate avatarCoord = new ImageCoordinate(100, 100, 100, 100);

        BufferedImage qrcode = util.getLocalFile("D:\\Images\\SB\\qrcode.jpg");
        ImageCoordinate qrcodeCoord = new ImageCoordinate(100, 220, 100, 100);

        StringCoordinate stringCoordinate = new StringCoordinate(32, 100, 500);

        InputStream inputStream = util.createPoster(bg, qrcode, qrcodeCoord, avatar, avatarCoord, "A张12ab⑶", stringCoordinate);

        FileOutputStream outputStream = new FileOutputStream("D:\\Images\\SB\\3.png");

        IOUtils.copy(inputStream, outputStream);
        IOUtils.closeQuietly(outputStream);
        IOUtils.closeQuietly(inputStream);

    }

}
