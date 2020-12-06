package com.bj25.study.java.queue;

public interface IQueue<T> {
    /**
     * 큐에 데이터를 삽입하는 메서드입니다.(Enqueue)
     * 
     * @param data
     */
    void addData(T data);

    /**
     * 큐에서 데이터를 가져오는 메서드입니다.(Dequeue)
     * 
     * @return
     */
    T getData();

    /**
     * 큐에 존재하는 데이터의 갯수를 반환하는 메서드입니다.
     * 
     * @return
     */
    int getElementCount();

    /**
     * 큐의 크기를 반환하는 메서드입니다.(빈 공간 포함)
     * 
     * @return
     */
    int size();

    /**
     * 큐가 비어있는지 확인하는 메서드입니다.
     * 
     * @return
     */
    boolean isEmpty();
}
