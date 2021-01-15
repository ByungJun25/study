package com.bj25.study.java.exceptions.checkedexceptions;

/**
 * InterruptedExceptionExample
 */
public class InterruptedExceptionExample {

    public static void main(String[] args) {
        subThread childThread = new subThread();
        childThread.start();
        childThread.interrupt();
    }

    public static class subThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000); // 10초가 sleep
            } catch (InterruptedException e) {
                System.out.println("InterruptedException 발생");
            }
        }
    }
}