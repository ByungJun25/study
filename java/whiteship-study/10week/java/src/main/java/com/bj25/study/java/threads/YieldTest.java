package com.bj25.study.java.threads;

public class YieldTest {
    public static class YourThread implements Runnable {
        @Override
        public void run() { // 실제 쓰레드 실행시 작동되는 부분.
            for (int i = 0; i < 10; i++) {
                Thread.yield();
                System.out.println(Thread.currentThread().getName() + ": " + i + "번째 출력");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                    return;
                }
            }
        }
    }

    public static class MyThread implements Runnable {
        @Override
        public void run() { // 실제 쓰레드 실행시 작동되는 부분.
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i + "번째 출력");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyThread(), "my");
        Thread thread2 = new Thread(new YourThread(), "your");
        thread2.start();
        thread.start(); // 쓰레드 실행.
    }
}
