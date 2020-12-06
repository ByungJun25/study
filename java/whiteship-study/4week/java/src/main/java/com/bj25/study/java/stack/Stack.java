package com.bj25.study.java.stack;

import java.util.Arrays;

public class Stack implements IStack {

    private int[] datas;
    private int elementCounts;

    public Stack() {
        this.datas = new int[10];
        this.elementCounts = 0;
    }

    @Override
    public int size() {
        return this.elementCounts;
    }

    @Override
    public void push(int data) {
        int size = (this.elementCounts + 1);
        if (size > this.datas.length) {
            this.grow(size);
        }

        this.datas[size - 1] = data;
        this.elementCounts += 1;
    }

    @Override
    public int pop() {
        int index = this.elementCounts - 1;
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("There is no data.");
        }

        int result = this.datas[index];
        this.elementCounts -= 1;
        this.datas[index] = 0;

        return result;
    }

    private void grow(int size) {
        if (size > Integer.MAX_VALUE) {
            throw new OutOfMemoryError();
        }

        this.datas = Arrays.copyOf(this.datas, size);
    }
}
