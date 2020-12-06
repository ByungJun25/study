package com.bj25.study.java.queue;

import java.util.Arrays;

public class Queue<T> implements IQueue<T> {

    private Object[] datas;

    private int head;

    private int tail;

    private int elementCount;

    public Queue() {
        this.datas = new Object[10];
        this.head = 0;
        this.tail = 0;
        this.elementCount = 0;
    }

    @Override
    public void addData(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is required!");
        }

        this.datas[this.tail] = data;
        this.elementCount += 1;

        if (!this.hasPlace()) {
            this.grow(this.elementCount + 1);
        }

        this.tail += 1;
        
    }

    private boolean hasPlace() {
        if (this.tail + 1 < this.datas.length) {
            return true;
        } else {
            for (int i = 0; i < this.datas.length; i++) {
                if (this.datas[i] == null && i < this.head) {
                    this.tail = i;
                    return true;
                }
            }
            return false;
        }
    }

    private void grow(int size) {
        if (size > Integer.MAX_VALUE) {
            throw new OutOfMemoryError();
        }

        this.datas = Arrays.copyOf(this.datas, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getData() {
        if (this.head == this.tail) {
            throw new ArrayIndexOutOfBoundsException("There is no data.");
        }

        T data = (T) this.datas[head];
        this.datas[head] = null;

        if (this.head + 1 > this.datas.length) {
            for (int i = 0; i < this.datas.length; i++) {
                if (this.datas[i] != null && i < this.tail) {
                    this.head = i;
                }
            }
        } else {
            this.head += 1;
        }

        this.elementCount -= 1;

        return data;
    }

    @Override
    public int getElementCount() {
        return this.elementCount;
    }

    @Override
    public int size() {
        return this.datas.length;
    }

    @Override
    public boolean isEmpty() {
        if (this.getElementCount() == 0) {
            return true;
        }
        return false;
    }
}
