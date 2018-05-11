package com.jdksource.util.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by books on 2017/4/5.
 */
public class Test {

    public static void main(String[] args) {

        Timer timer = new Timer();

        timer.schedule(new MyTimerTask(), 2000 );

    }
     static class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("TimerTask..." + Thread.currentThread().getName());
        }
    }
}



