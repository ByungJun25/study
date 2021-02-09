package com.bj25.study.java.threads;

public class PriorityTest {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(new MyThread(), "first");
        Thread second = new Thread(new MyThread(), "second");
        Thread third = new Thread(new MyThread(), "third");

        third.setPriority(Thread.MAX_PRIORITY);
        second.setPriority(Thread.MIN_PRIORITY);

        first.start();
        second.start();
        third.start();

        Thread.sleep(5000);
        Thread.currentThread().getThreadGroup().interrupt();
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
