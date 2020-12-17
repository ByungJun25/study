package com.bj25.study.java.binarytree.model;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BinarySearchTree implements Tree {

    private static final Logger log = Logger.getLogger(BinarySearchTree.class.getName());

    private Node root;

    public BinarySearchTree(int data) {
        this.root = new Node(data);
    }

    @Override
    public void add(int data) {
        this.root = this.addRecursive(this.root, data);
    }

    private Node addRecursive(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (node.getData() > data) {
            node.addLeft(this.addRecursive(node.getLeft(), data));
        } else if (node.getData() < data) {
            node.addRight(this.addRecursive(node.getRight(), data));
        } else {
            log.warning("The data already exists.");
        }

        return node;
    }

    @Override
    public void delete(int data) {
        this.root = this.deleteRecursive(this.root, data);
    }

    private Node deleteRecursive(Node node, int data) {
        if(node == null) {
            throw new NoSuchElementException("There is no Node which has the data.");
        }

        if(node.getData() == data) {
            if(!node.hasNext()) {
                return null;
            }

            if(!node.hasLeft()) {
                return node.getRight();
            }

            if(!node.hasRight()) {
                return node.getLeft();
            }
            
            final int smallestData = this.getSmallestData(node.getRight());
            node.updateData(smallestData);
            node.addRight(this.deleteRecursive(node.getRight(), smallestData));
            return node;
        }

        if(node.getData() > data) {
            node.addLeft(this.deleteRecursive(node.getLeft(), data));
        } else if(node.getData() < data) {
            node.addRight(this.deleteRecursive(node.getRight(), data));
        }

        return node;
    }

    @Override
    public void remove(int data) {
        this.root = this.removeRecursive(this.root, data);
    }

    private Node removeRecursive(Node node, int data) {
        if(node == null) {
            return null;
        }

        if(node.getData() == data) {
            if(!node.hasNext()) {
                return null;
            }

            if(!node.hasLeft()) {
                return node.getRight();
            }

            if(!node.hasRight()) {
                return node.getLeft();
            }
            
            final int smallestData = this.getSmallestData(node.getRight());
            node.updateData(smallestData);
            node.addRight(this.removeRecursive(node.getRight(), smallestData));
            return node;
        }

        if(node.getData() > data) {
            node.addLeft(this.removeRecursive(node.getLeft(), data));
        } else if(node.getData() < data) {
            node.addRight(this.removeRecursive(node.getRight(), data));
        }

        return node;
    }

    private int getSmallestData(Node node) {
        return (!node.hasLeft()) ? node.getData() : this.getSmallestData(node.getLeft());
    }

    @Override
    public boolean contain(int data) {
        return containRecursive(this.root, data);
    }

    private boolean containRecursive(Node node, int data) {
        if(node == null) {
            return false;
        }

        if(node.getData() == data) {
            return true;
        }

        return node.getData() > data ? this.containRecursive(node.getLeft(), data) : this.containRecursive(node.getRight(), data);
    }

    @Override
    public Node element(int data) {
        return this.elementRecursive(this.root, data);
    }

    private Node elementRecursive(Node node, int data) {
        if(node == null) {
            return null;
        }

        if(node.getData() > data) {
            return this.elementRecursive(node.getLeft(), data);
        } else if(node.getData() < data) {
            return this.elementRecursive(node.getRight(), data);
        } else {
            return node;
        }
    }

    @Override
    public Node peek(int data) {
        return this.peekRecursive(this.root, data);
    }

    private Node peekRecursive(Node node, int data) {
        if(node == null) {
            throw new NoSuchElementException("There is no data!");
        }

        if(node.getData() > data) {
            return this.peekRecursive(node.getLeft(), data);
        } else if(node.getData() < data) {
            return this.peekRecursive(node.getRight(), data);
        } else {
            return node;
        }
    }

    @Override
    public int size() {
        if(this.root == null) {
            return 0;
        }

        int count = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);

        while (!queue.isEmpty()) {
            Node target = queue.remove();
            count += 1;

            if (target.getLeft() != null) {
                queue.add(target.getLeft());
            }

            if (target.getRight() != null) {
                queue.add(target.getRight());
            }
        }

        return count;
    }

}
