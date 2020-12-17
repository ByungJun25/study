package com.bj25.study.java.binarytree.model;

import java.util.LinkedList;
import java.util.Queue;

import com.bj25.study.java.binarytree.enums.Order;

public interface Tree {

    /**
     * 데이터를 추가하는 메소드입니다.
     * 
     * @param data
     */
    public void add(int data);

    /**
     * 데이터를 삭제하는 메소드입니다.
     * 
     * @param data
     */
    public void remove(int data);

    /**
     * 데이터를 삭제하는 메소드입니다.
     * 
     * @param data
     * @throws NoSuchElementException if the data wasn't existed.
     */
    public void delete(int data);

    /**
     * 주어진 데이터를 가진 노드를 찾는 메소드입니다.
     * 
     * @param data
     * @return {@code Node}, if it has the targeted data or {@code null} if there is no data.
     */
    public Node element(int data);

    /**
     * 주어진 데이터를 가진 노드를 찾는 메소드입니다.
     * 
     * @param data
     * @return {@code Node}, if it has the targeted data.
     * @throws NoSuchElementException if the data wasn't existed.
     */
    public Node peek(int data);

    /**
     * 주어진 데이터가 트리에 존재하는지 확인하는 메소드입니다.
     * 
     * @param data
     * @return {@code true}, if the data already exists.
     */
    public boolean contain(int data);

    /**
     * 트리의 원소 갯수를 반환하는 메소드입니다.
     * 
     * @return
     */
    public int size();

    public static void bfs(Node node) {
        System.out.println("BFS start!");
        if (node == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node target = queue.remove();

            System.out.print(target.getData() + " ");

            if (target.getLeft() != null) {
                queue.add(target.getLeft());
            }

            if (target.getRight() != null) {
                queue.add(target.getRight());
            }
        }
        System.out.println();
        System.out.println("BFS end!");
        System.out.println();
    }

    public static void dfs(Node node, Order order) {
        switch (order) {
            case PRE:
                System.out.println("DFS start! - pre order");
                preOrder(node);
                break;
            case IN:
                System.out.println("DFS start! - in order");
                inOrder(node);
                break;
            case POST:
                System.out.println("DFS start! - post order");
                postOrder(node);
                break;
        }
        System.out.println();
        System.out.println("DFS end!");
        System.out.println();
    }

    private static void preOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.getData());
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    private static void inOrder(Node node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(" " + node.getData());
            inOrder(node.getRight());
        }
    }

    private static void postOrder(Node node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print(" " + node.getData());
        }
    }
}
