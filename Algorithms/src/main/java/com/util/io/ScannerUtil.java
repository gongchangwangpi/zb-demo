package com.util.io;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author bo6.zhang
 * @date 2021/4/28
 */
public class ScannerUtil {

  public static void main(String[] args) {

    int thread = 4;
    scanHandle(thread, (s) -> System.out.println(s));

  }

  public static void scanHandle(int thread, Consumer<String> consumer) {
    ExecutorService executorService = Executors.newFixedThreadPool(thread);

    Scanner scanner = new Scanner(System.in);

    while (true) {

      if (scanner.hasNext()) {
        String next = scanner.next();

        executorService.execute(() -> {
          consumer.accept(next);
        });

      }

    }
  }

}
