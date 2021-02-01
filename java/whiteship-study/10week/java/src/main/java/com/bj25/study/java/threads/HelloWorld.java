package com.bj25.study.java.threads;

import java.util.concurrent.TimeUnit;

/**
 * HelloWorld
 */
public class HelloWorld {

    public static class HelloRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("thread");
        }

    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Sub thread!");
        }
    }

    public static void main(String[] args) {
        Thread thread = new MyThread();
        thread.start();

        Thread thread2 = new Thread(new HelloRunnable());
        thread2.start();
    }
}