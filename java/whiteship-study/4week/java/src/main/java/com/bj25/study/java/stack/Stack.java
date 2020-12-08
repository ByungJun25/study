package com.bj25.study.java.stack;

import java.util.Arrays;

/**
 * int 배열을 이용한 Stack 클래스입니다.
 * 
 * @author BJ25
 */
public class Stack implements IStack {

    private int[] datas;
    private int elementCounts;

    /**
     * 기본 생성자입니다. 크기가 10인 배열을 초기화합니다.
     * 
     */
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

    /**
     * 스택의 크기가 넣고자 하는 데이터의 양보다 작을때, 배열의 크기를 늘려주는 메서드입니다.
     * 
     * @param size
     */
    private void grow(int size) {
        if (size > Integer.MAX_VALUE) {
            throw new OutOfMemoryError();
        }

        this.datas = Arrays.copyOf(this.datas, size);
    }
}
