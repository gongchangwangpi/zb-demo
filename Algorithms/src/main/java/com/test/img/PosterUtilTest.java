package com.test.img;


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

/**
 * @author bo6.zhang
 * @date 2021/4/1
 */
public class PosterUtilTest {

    public BufferedImage getRemoteImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            try {
                return ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException me) {
            me.printStackTrace();
        }
        return null;
    }

    public BufferedImage getLocalFile(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream createPoster(String backgroundUrl,
                                    String nickname, int size, int x, int y,
                                    String avatarUrl, int ax, int ay, int height, int width) {
        BufferedImage bg = getLocalFile(backgroundUrl);
        BufferedImage avatar = getLocalFile(avatarUrl);

        Graphics2D rng = bg.createGraphics();
        rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));

        rng.setFont(getSupportChineseFont(size));
        rng.setColor(Color.black);
        rng.drawString(nickname, x, y);

        rng.drawImage(avatar, ax, ay, height, width, null);

        rng.dispose();
        bg.flush();
        return bufferedImageToInputStream(bg);
    }

    private static Font getSupportChineseFont(int size) {
        String s = "你好⑴×123abc";
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : ge.getAllFonts()) {
            if (font.canDisplayUpTo(s) == -1) {
                System.out.println("返回Font: " + font);
                return new Font(font.getFontName(), Font.BOLD, size);
            }
        }
        System.out.println("返回默认Font: Serif");
        return new Font("Serif", Font.BOLD, size);
    }

    public InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        PosterUtilTest util = new PosterUtilTest();

        String posterUrl = "D://images//poster.jpg";
        String avatarUrl = "D://images//avatar.jpg";

        InputStream inputStream = util.createPoster(posterUrl, "A张12ab⑶", 32, 400, 150,
                avatarUrl, 200, 500, 200, 200);

        FileOutputStream outputStream = new FileOutputStream("4.png");

        int len = inputStream.available();
        byte[] bytes = new byte[len];

        inputStream.read(bytes);
        outputStream.write(bytes);

        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

}
