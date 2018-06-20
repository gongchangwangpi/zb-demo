package com.jdksource.nio.chapter5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangbo
 */
public class SimpleGrep {

    public static void main(String[] argv) throws Exception {
        if (argv.length < 2) {
            System.out.println("Usage: regex file [ ... ]");
            return;
        }
        Pattern pattern = Pattern.compile(argv[0]);
        Matcher matcher = pattern.matcher("");
        for (int i = 1; i < argv.length; i++) {
            String file = argv[i];
            BufferedReader br = null;
            String line;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                System.err.println("Cannot read '" + file + "': " + e.getMessage());
                continue;
            }
            while ((line = br.readLine()) != null) {
                matcher.reset(line);
                if (matcher.find()) {
                    System.out.println(file + ": " + line);
                }
            }
            br.close();
        }
    }


}
