package com.bj25.study.java.threads;

public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new MyThread());
        myThread.start();
        System.out.println("Started Thread and call join");
        myThread.join();
        System.out.println("Finished");
    }

    public static class MyThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + "번째 출력");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                    return;
                }
            }
        }
    } 
}
