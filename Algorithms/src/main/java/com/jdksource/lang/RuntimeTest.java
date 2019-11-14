package com.jdksource.lang;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2019-11-13
 */
@Slf4j
public class RuntimeTest {

    public static void main(String[] args) throws Exception {
//        Process process = Runtime.getRuntime().exec("ps -ef");

//        Process process = Runtime.getRuntime().exec("java com.jdksource.lang.ProcessTestDemo");
//        Process process = Runtime.getRuntime().exec("java -version");

        String command = "top";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(command);

                    InputStream inputStream = process.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    System.out.println(" ----- print ----- ");

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    IOUtils.closeQuietly(bufferedReader);

                    System.out.println(" ----- error ----- ");

                    InputStream errorStream = process.getErrorStream();
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

                    String line1;
                    while ((line1 = errorReader.readLine()) != null) {
                        System.out.println(line1);
                    }
                    IOUtils.closeQuietly(bufferedReader);

                    System.out.println(" ----- error ----- end");

                    //        TimeUnit.SECONDS.sleep(5);
                    //        process.destroy();
                } catch (IOException e) {
                    log.error(" sub thread IOException", e);
                }
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(5);

        // interrupt 不能正常中断在执行中的process，stop可以
//        thread.interrupt();
        thread.stop();

        log.warn("main thread ----- >>>> end");

    }

}
