package com.jdksource.util.timer;

import java.util.Timer;
import java.util.TimerTask;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by books on 2017/4/5.
 */
@Slf4j
public class Test {

    public static void main(String[] args) {

        Timer timer = new Timer();

        timer.schedule(new MyTimerTask(), 0, 2000);
        timer.schedule(new MyTimerTask(), 0, 3000);

    }
     static class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            log.info("-----");
        }
    }
}



