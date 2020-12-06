package com.bj25.study.java.queue;

import com.bj25.study.java.linkedlist.ListNode;

public class ListNodeQueue<T> implements IQueue<T> {

    private ListNode<T> datas;

    public ListNodeQueue() {
    }

    @Override
    public void addData(T data) {
        ListNode<T> node = new ListNode<>(data);

        if (this.datas == null) {
            this.datas = node;
        } else {
            this.datas.getLast().addNext(node);
        }
    }

    @Override
    public T getData() {
        if (this.datas == null) {
            throw new ArrayIndexOutOfBoundsException("There is no data");
        }

        ListNode<T> dataNode = this.datas;
        this.datas = this.datas.getNext();

        dataNode.addNext(null);
        return dataNode.getData();
    }

    @Override
    public int getElementCount() {
        if (this.datas == null) {
            return 0;
        }

        int count = 1;
        ListNode<T> data = this.datas;
        while (data.hasNext()) {
            data = data.getNext();
            count += 1;
        }

        return count;
    }

    @Override
    public int size() {
        return this.getElementCount();
    }

    @Override
    public boolean isEmpty() {
        if (this.getElementCount() == 0) {
            return true;
        }
        return false;
    }

}
