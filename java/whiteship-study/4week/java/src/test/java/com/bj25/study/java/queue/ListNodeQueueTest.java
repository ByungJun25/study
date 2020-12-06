package com.bj25.study.java.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListNodeQueueTest {
    
    @DisplayName("Queue 생성 테스트")
    @Test
    public void createQueueTest() {
        IQueue<Integer> queue = new ListNodeQueue<>();

        assertNotNull(queue);
        assertEquals(0, queue.getElementCount());
    }

    @DisplayName("Queue에 원소 100개 추가")
    @Test
    public void enQueueTest() {
        IQueue<Integer> queue = new ListNodeQueue<>();

        for (int i = 0; i < 100; i++) {
            queue.addData(i);
        }

        assertEquals(100, queue.getElementCount());
    }

    @DisplayName("Queue에 모든 원소 빼기")
    @Test
    public void deQueueTest() {
        IQueue<Integer> queue = new ListNodeQueue<>();

        for (int i = 0; i < 100; i++) {
            queue.addData(i);
        }

        int i = 0;
        while(!queue.isEmpty()) {
            int data = queue.getData();
            assertEquals(i, data);
            i += 1;
        }

        assertEquals(true, queue.isEmpty());
    }

    @DisplayName("Queue 복합 테스트")
    @Test
    public void EnAndDequeueTest() {
        IQueue<Integer> queue = new ListNodeQueue<>();

        for (int i = 0; i < 50; i++) {
            queue.addData(i+1);
        }

        for(int i = 0; i < 25; i++) {
            queue.getData();
        }

        for(int i = 0; i < 10; i++) {
            queue.addData(i);
        }

        assertEquals(35, queue.getElementCount());
    }

    @DisplayName("Queue의 원소 갯수 보다 더 많이 빼기")
    @Test
    public void dequeueOverSizeTest() {
        IQueue<Integer> queue = new ListNodeQueue<>();

        for (int i = 0; i < 20; i++) {
            queue.addData(i+1);
        }
        
        for (int i = 0; i < 20; i++) {
            queue.getData();
        }
        
        assertEquals(0, queue.getElementCount());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> queue.getData());
    }
}
