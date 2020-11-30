package com.bj25.study.java.linkedlist;

import lombok.Getter;

/**
 * Linkedlist를 구성하는 기본 요소인 Node를 구성한 class입니다.
 * Node는 데이터 필드와 이전, 다음 값을 가리키는 참조값을 가집니다.
 * 
 * @author  BJ25
 * @param <T> data의 타입을 정의하기 위해 선언했습니다.
 */
@Getter
public class Node<T> {
    
    // 실제 Node에 저장된 data입니다.
    private T data;

    // 다음 노드를 가리키는 참조값입니다.
    private Node<T> next;

    // 이전 노드를 가리키는 참조값입니다.
    private Node<T> before;

    public Node(T data, Node<T> next, Node<T> before) {
        this.data = data;
        this.next = next;
        this.before = before;
    }
    
}
