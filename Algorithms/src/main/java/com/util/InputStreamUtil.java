package com.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 判断流的格式
 */
public class InputStreamUtil {

    private static Logger log = LoggerFactory.getLogger(InputStreamUtil.class);

    /**
     * 判断流是否是图片格式
     *
     * @param is
     * @return
     */
    public static boolean isImage(InputStream is) throws IOException {
        byte[] b = new byte[4];
        try {
            is.read(b, 0, b.length);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        String type = bytesToHexString(b).toUpperCase();
        log.info("file type :" + type);

        if (type.contains("FFD8FF")) {    //jpg
            return true;
        } else if (type.contains("89504E47")) {    //png
            return true;
        } else if (type.contains("47494638")) {    //gif
            return true;
        } else if (type.contains("424D")) {    //bmp
            return true;
        }
        return false;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}
