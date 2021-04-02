package com.test.img;

import java.awt.*;

public class FontTest {

    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font[] fonts = ge.getAllFonts();
        for (Font font : fonts) {
            System.out.println(font);
        }

        System.out.println("============");

        Font f = getSupportChineseFont(26);
        System.out.println(f);
        System.out.println(f.getName());

        System.out.println("============");

        Font font = new Font("Serif", Font.BOLD, 26);
        System.out.println(font.getFontName());
        System.out.println(font.canDisplayUpTo("12344sfdv./'你"));

    }

    private static Font getSupportChineseFont(int size) {
        String s = "你好⑴×";
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : ge.getAllFonts()) {
            if (font.canDisplayUpTo(s) == -1) {
                return new Font(font.getFontName(), Font.BOLD, size);
            }
        }
        throw new RuntimeException("暂不支持中文字体");
    }

}
