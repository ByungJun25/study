package com.bj25.study.java.binarytree.model;

import lombok.Getter;

/**
 * <p>Tree에 저장될 노드를 정의한 클래스입니다.
 * 
 * @author BJ25
 */
@Getter
public class Node {

    private int data;
    private Node left;
    private Node right;

    /**
     * 주어진 데이터로 자식이 없는 노드를 생성하는 생성자입니다.
     * 
     * @param data
     */
    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /**
     * left 자식을 추가하는 메소드입니다.
     * 
     * @param node
     * @return
     */
    public Node addLeft(Node node) {
        
        this.left = node;
        return this.left;
    }

    /**
     * right 자식을 추가하는 메소드입니다.
     * 
     * @param node
     * @return
     */
    public Node addRight(Node node) {
        this.right = node;
        return this.right;
    }

    /**
     * 현재 노드의 데이터 값을 변경하는 메소드입니다.
     * 
     * @param data
     */
    public void updateData(int data) {
        this.data = data;
    }

    /**
     * 자식의 존재여부를 확인하는 메소드입니다.
     * 
     * @return
     */
    public boolean hasNext() {
        if(this.hasLeft() || this.hasRight()) {
            return true;
        }
        return false;
    }

    /**
     * right 자식의 존재여부를 확인하는 메소드입니다.
     * 
     * @return
     */
    public boolean hasRight() {
        if(this.right != null) {
            return true;
        }
        return false;
    }

    /**
     * left 자식의 존재여부를 확인하는 메소드입니다.
     * 
     * @return
     */
    public boolean hasLeft() {
        if(this.left != null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + data;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (data != other.data)
            return false;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }

}
