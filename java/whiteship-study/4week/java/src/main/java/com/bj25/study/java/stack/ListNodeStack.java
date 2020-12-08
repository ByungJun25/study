package com.bj25.study.java.stack;

import com.bj25.study.java.linkedlist.ListNode;

/**
 * 앞서 만든 ListNode를 이용한 Stack 클래스입니다.
 * 
 * @author BJ25
 */
public class ListNodeStack implements IStack {

    private ListNode<Integer> head;

    public ListNodeStack() {
        this.head = ListNode.createHead(null);
    }

    @Override
    public int size() {
        return (ListNode.getSize(this.head) - 1);
    }

    @Override
    public void push(int data) {
        ListNode<Integer> nodeToAdd = new ListNode<>(data);
        int position = this.size() + 1;

        ListNode.add(this.head, nodeToAdd, position);
    }

    @Override
    public int pop() {
        if (this.size() == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int data = this.head.getLast().getData();
        this.head.removeLast();

        return data;
    }

}
