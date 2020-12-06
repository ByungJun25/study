package com.bj25.study.java.stack;

public interface IStack {

    /**
     * 스택의 크기를 반환하는 메서드입니다.
     * 
     * @return
     */
    int size();

    /**
     * 스택에 데이터를 추가하는 메서드입니다.
     * 
     * @param data
     */
    void push(int data);

    /**
     * 스택에서 데이터를 가져오는 메서드입니다.
     * 
     * @return
     */
    int pop();

}
