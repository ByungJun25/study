package com.bj25.study.java.threads;

public class DeamonThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new MyDaemonThread());
        myThread.setDaemon(true);
        myThread.start();
        Thread.sleep(5000);
    }

    public static class MyDaemonThread implements Runnable {

        @Override
        public void run() {
            Thread child = new Thread(new ChildDeamonThread());
            child.start();
            for (int i = 0; i < 1000; i++) {
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

    public static class ChildDeamonThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i + "번째 출력 - child");
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
