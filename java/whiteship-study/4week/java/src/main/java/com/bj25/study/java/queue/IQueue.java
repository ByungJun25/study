package com.bj25.study.java.queue;

public interface IQueue<T> {
    void addData(T data);

    T getData();

    int getElementCount();

    int size();

    boolean isEmpty();
}
