package com.bj25.study.java.binarytree.model;

import lombok.Getter;

@Getter
public class Node {

    private int data;
    private Node left;
    private Node right;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public Node addLeft(Node node) {
        
        this.left = node;
        return this.left;
    }

    public Node addRight(Node node) {
        this.right = node;
        return this.right;
    }

    public void updateData(int data) {
        this.data = data;
    }

    public boolean hasNext() {
        if(this.hasLeft() || this.hasRight()) {
            return true;
        }
        return false;
    }

    public boolean hasRight() {
        if(this.right != null) {
            return true;
        }
        return false;
    }

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
