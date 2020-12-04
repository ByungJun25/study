package com.bj25.study.java.linkedlist;

/**
 * <p>
 * LinkedList란 각 노드가 데이터와 포인터를 가지고 한 줄로 연결되어 있는 방식으로 데이터를 저장하는 자료구조입니다.
 * <p>
 * LinkedList에는 다음의 종류가 있습니다.
 * <ol>
 * <li>단일 연결 리스트</li>
 * <li>이중 연결 리스트</li>
 * <li>원형 연결 리스트</li>
 * 
 * @author BJ25
 */
public class LinkedListManagment {

    public static <T> ListNode<T> add(ListNode<T> head, ListNode<T> nodeToAdd, int position) {
        if(head == null) {
            throw new IllegalArgumentException("The head node is required!");
        }

        if(position < 0) {
            throw new IllegalArgumentException("The position cannot be negative");
        }

        if(position == 0) {
            nodeToAdd.setNext(head);
            return nodeToAdd;
        }

        ListNode<T> targetNode = null;

        while(position-- > 0) {
            if(head.hasNext()) {
                targetNode = head.getNext();
            } else {
                throw new IllegalArgumentException("The position is over the list size!");
            }
        }

        ListNode<T> temp = targetNode.getNext();
        targetNode.setNext(nodeToAdd);
        nodeToAdd.setNext(temp);
        
        return nodeToAdd;
    }

    public static <T> ListNode<T> remove(ListNode<T> head, int positionToRemove) {
        if(head == null) {
            throw new IllegalArgumentException("Head node is required!");
        }

        if(positionToRemove < 0) {
            throw new IllegalArgumentException("The position cannot be negative");
        }

        if(positionToRemove == 0) {
            return head.getNext();
        }

        ListNode<T> before = null;
        ListNode<T> targetNode = null;

        while(positionToRemove-- > 0) {
            if(head.hasNext()) {
                if(targetNode != null) {
                    before = targetNode;
                }
                targetNode = head.getNext();
            } else {
                throw new IllegalArgumentException("The position is over the list size!");
            }
        }
        
        before.setNext(targetNode.getNext());
        
        return targetNode;
    }

    public static <T> boolean contains(ListNode<T> head, ListNode<T> nodeToCheck) {
        if(head == null) {
            throw new IllegalArgumentException("Head node is required!");
        }

        if(head.getData().equals(nodeToCheck.getData())) {
            return true;
        }

        ListNode<T> target = head.getNext();

        while(target != null) {
            if(target.getData().equals(nodeToCheck.getData())) {
                return true;
            }
            target = target.getNext();
        }
        
        return false;
    }
    
}
