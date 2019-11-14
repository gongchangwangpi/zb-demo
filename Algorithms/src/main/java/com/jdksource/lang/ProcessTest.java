package com.jdksource.lang;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * java 执行shell，默认是执行/bin下的命令
 * Linux需要将命令建立软连接到/bin
 *
 * No such file or directory
 *
 * @author zhangbo
 * @date 2019-10-31
 */
@Slf4j
public class ProcessTest {

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/zhangbo/IdeaProjects/zb-demo/Algorithms/target/classes");
        System.out.println(file.getAbsolutePath());

        Process process = new ProcessBuilder()
//                .directory(new File("/Users/zhangbo/IdeaProjects/zb-demo/Algorithms/target/classes"))
//                .command("java com.jdksource.lang.ProcessTestDemo") // 不能执行
                .command("ps -ef |grep java") // 可以执行
                .redirectOutput(new File("/Users/zhangbo/IdeaProjects/zb-demo/Algorithms/target/classes/Process.log"))
                .start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            log.error(s);
        }
        while ((s = stdError.readLine()) != null) {
            log.error(s);
        }

        TimeUnit.SECONDS.sleep(5);

        process.destroy();

    }

}
