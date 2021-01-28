package com.bj25.study.java.linkedlist;

import lombok.Getter;

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
 * Linkedlist를 구성하는 기본 요소인 Node를 구성한 class입니다. Node는 데이터 필드와 이전, 다음 값을 가리키는 참조값을
 * 가집니다.
 * 
 * @author BJ25
 * @param <T> data의 타입을 정의하기 위해 선언했습니다.
 */
@Getter
public class ListNode<T> {

    // 실제 Node에 저장된 data입니다.
    private T data;

    // 다음 노드를 가리키는 참조값입니다.
    private ListNode<T> next;

    public ListNode(T data) {
        this.data = data;
        this.next = null;
    }

    /**
     * 다음 노드의 존재 여부를 확인하는 메서드입니다.
     * 
     * @return
     */
    public boolean hasNext() {
        if (this.next == null) {
            return false;
        }
        return true;
    }

    /**
     * 노드를 추가하는 메서드입니다.
     * 
     * @param next
     */
    public void addNext(ListNode<T> next) {
        this.next = next;
    }

    /**
     * 마지막 노드를 반환하는 메서드입니다.
     * 
     * @return
     */
    public ListNode<T> getLast() {
        ListNode<T> last = this;

        while (last.hasNext()) {
            last = last.getNext();
        }

        return last;
    }

    /**
     * 마지막 노드를 제거하는 메서드입니다.
     */
    public void removeLast() {
        ListNode<T> pre = null;
        ListNode<T> last = this;

        while (last.hasNext()) {
            pre = last;
            last = last.getNext();
        }

        if (pre != null) {
            pre.addNext(null);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((next == null) ? 0 : next.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListNode other = (ListNode) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (next == null) {
            if (other.next != null)
                return false;
        } else if (!next.equals(other.next))
            return false;
        return true;
    }

    /**
     * <p>
     * 주어진 데이터를 가진 ListNode를 하나 생성합니다.
     * 
     * @param <T>
     * @param data
     * @return
     */
    public static <T> ListNode<T> createHead(T data) {
        return new ListNode<T>(data);
    }

    public static <T> int getSize(ListNode<T> head) {
        int size = 1;
        ListNode<T> target = head;

        while (target.hasNext()) {
            size += 1;
            target = target.getNext();
        }

        return size;
    }

    /**
     * <p>
     * Head 노드를 기준으로 주어진 position에 파라미터로 받은 노드를 추가합니다.
     * <p>
     * 만약 position이 0인경우 파라미터로 받은 노드가 헤더가 되고 헤더를 노드의 맨 끝에 연결합니다.
     * <p>
     * Head 노드의 사이즈보다 큰 postion이 들어올 경우, IllegalArgumentException 발생합니다.
     * 
     * @param <T>       데이터 타입
     * @param head      - required
     * @param nodeToAdd
     * @param position  - 사이즈보다 작아야합니다.
     * @return 추가된 노드
     */
    public static <T> ListNode<T> add(ListNode<T> head, ListNode<T> nodeToAdd, int position) {
        if (head == null) {
            throw new IllegalArgumentException("The head node is required!");
        }

        if (position < 0) {
            throw new IllegalArgumentException("The position cannot be negative");
        }

        // Changed head node.
        if (position == 0) {
            nodeToAdd.getLast().addNext(head);
            return nodeToAdd;
        }

        ListNode<T> targetNode = head;

        while (position-- > 0) {
            if (targetNode.hasNext()) {
                targetNode = targetNode.getNext();
            } else {
                break;
            }
        }

        if (position != 0) {
            throw new IllegalArgumentException("The position is over the list size!");
        }

        ListNode<T> temp = targetNode.getNext();
        targetNode.addNext(nodeToAdd);
        nodeToAdd.addNext(temp);

        return nodeToAdd;
    }

    /**
     * <p>
     * Head 노드를 기준으로 주어진 position에 해당하는 노드를 제거합니다.
     * <p>
     * 만약 position이 0인경우, 헤더 노드를 제거합니다.
     * <p>
     * Head 노드의 사이즈보다 큰 postion이 들어올 경우, IllegalArgumentException 발생합니다.
     * 
     * 
     * @param <T>              데이터 타입
     * @param head             - required
     * @param positionToRemove
     * @return 제거된 노드
     */
    public static <T> ListNode<T> remove(ListNode<T> head, int positionToRemove) {
        if (head == null) {
            throw new IllegalArgumentException("Head node is required!");
        }

        if (positionToRemove < 0) {
            throw new IllegalArgumentException("The position cannot be negative");
        }

        // remove head node.
        if (positionToRemove == 0) {
            head.addNext(null);
            return head;
        }

        ListNode<T> pre = null;
        ListNode<T> targetNode = head;

        while (positionToRemove-- > 0) {
            if (targetNode.hasNext()) {
                pre = targetNode;
                targetNode = targetNode.getNext();
            } else {
                break;
            }
        }

        if (positionToRemove != -1) {
            throw new IllegalArgumentException("The position is over the list size!");
        }

        if (targetNode.hasNext()) {
            pre.addNext(targetNode.getNext());
        } else {
            pre.addNext(null);
        }

        targetNode.addNext(null);

        return targetNode;
    }

    /**
     * <p>
     * 주어진 노드가 head 노드의 리스트에 포함되었는지 확인합니다.
     * 
     * @param <T>
     * @param head
     * @param nodeToCheck
     * @return
     */
    public static <T> boolean contains(ListNode<T> head, ListNode<T> nodeToCheck) {
        if (head == null) {
            throw new IllegalArgumentException("Head node is required!");
        }

        if (head.equals(nodeToCheck)) {
            return true;
        }

        ListNode<T> target = head.getNext();

        while (target != null) {
            if (target.equals(nodeToCheck)) {
                return true;
            }
            target = target.getNext();
        }

        return false;
    }

}
