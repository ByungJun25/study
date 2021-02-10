package com.bj25.study.java.threads;

import lombok.RequiredArgsConstructor;

public class SynchronizedTest {

    public static void main(String[] args) {
        Data data = new Data();

        Thread first = new Thread(new MyThread(data), "first");
        Thread second = new Thread(new MyThread(data), "second");
        Thread third = new Thread(new MyThread(data), "third");
        first.start();
        second.start();
        third.start();
    }

    @RequiredArgsConstructor
    public static class MyThread implements Runnable {
        private final Data data;

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    this.data.setValue(this.data.getValue() + 1);
                    System.out.println(Thread.currentThread().getName() + ": data.value: " + this.data.getValue());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }

            }
        }
    }

    public static class Data {
        private int value;

        public synchronized void setValue(int value) {
            this.value = value;
        }

        public synchronized int getValue() {
            return this.value;
        }
    }
}
