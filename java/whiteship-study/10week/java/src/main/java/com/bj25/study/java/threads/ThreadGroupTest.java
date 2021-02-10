package com.bj25.study.java.threads;

public class ThreadGroupTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group = new ThreadGroup("ThreadGroup");
        Thread t1 = new Thread(group, new MyThread(), "First");
        Thread t2 = new Thread(group, new MyThread(), "Second");
        Thread t3 = new Thread(group, new MyThread(), "Third");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(5000);
        group.interrupt();
    }

    public static class MyThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i + "번째 출력");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
