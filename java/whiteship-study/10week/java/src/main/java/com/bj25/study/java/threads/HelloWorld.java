package com.bj25.study.java.threads;

import java.util.concurrent.TimeUnit;

/**
 * HelloWorld
 */
public class HelloWorld {

    public static class HelloRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println("thread");
                if (Thread.interrupted()) {
                    System.out.println("thread interrupted");
                    return;
                }
                
            }
        }

    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new HelloRunnable());
        thread1.start();
        if (Thread.interrupted()) {
            System.out.println("main thread interrupted");
            return;
        }
        System.out.println("main thread was finished");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            System.out.println("main interrupted!");
        }
        thread1.interrupt();

    }
}