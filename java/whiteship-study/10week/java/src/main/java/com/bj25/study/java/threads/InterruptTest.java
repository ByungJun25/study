package com.bj25.study.java.threads;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {

        Thread myThread = new Thread(new MyThread());
        myThread.start();

        Thread.sleep(5000);
        
        myThread.interrupt();
    }

    public static class MyThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + "번째 출력");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                    return;
                }
            }
        }
    }

    public static class MyThread2 implements Runnable {

        @Override
        public void run() {
            while (true) {
                if(Thread.interrupted()) {
                    System.out.println("interrupted!");
                    break;
                }
            }
        }
    }
}
