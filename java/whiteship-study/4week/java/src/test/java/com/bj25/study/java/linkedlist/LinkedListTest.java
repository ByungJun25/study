package com.bj25.study.java.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LinkedList 테스트 클래스")
public class LinkedListTest {

    @DisplayName("Head Node 생성 테스트")
    @Test
    public void createHeadTest() {
        // given
        ListNode<Integer> node = new ListNode<Integer>(1);

        // then
        assertEquals(1, node.getData());
        assertEquals(null, node.getNext()); // head node만 생성한 경우 다음 노드가 없어야 함.
    }

    @DisplayName("Head node에 노드 100개 추가하기")
    @Test
    public void add100Test() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);

        // when
        for (int i = 0; i < 100; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode<Integer> result = ListNode.add(head, nodeToAdd, (i + 1));

            // then
            assertEquals(nodeToAdd, result); // 추가한 결과 값은 추가한 노드여야 함.
        }

        // then
        assertEquals(101, ListNode.getSize(head)); // 100개를 추가했으므로, 헤더 노드 포함 101개가 되어야 함.
    }

    @DisplayName("Head node가 null일때, node를 추가한 경우")
    @Test
    public void addToNullHeadTest() {
        // given
        ListNode<Integer> nodeToAdd = new ListNode<>(1);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.add(null, nodeToAdd, 1)); // null에서
    }

    @DisplayName("0 position 값을 줬을때, node를 추가한 경우")
    @Test
    public void addZeroPositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToAdd = new ListNode<>(1);
        int position = 0;

        // when
        ListNode<Integer> result = ListNode.add(head, nodeToAdd, position);

        // then
        assertEquals(nodeToAdd, result);
        assertNotNull(nodeToAdd.getNext());
        assertEquals(head, nodeToAdd.getLast());
    }

    @DisplayName("size보다 큰 position값을 줬을때, node를 추가한 경우")
    @Test
    public void addOverSizePositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToAdd = new ListNode<>(1);
        int position = ListNode.getSize(head) + 1;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.add(head, nodeToAdd, position));
    }

    @DisplayName("음수 position값을 줬을때, node를 추가한 경우")
    @Test
    public void addNegativePositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToAdd = new ListNode<>(1);
        int position = -1;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.add(head, nodeToAdd, position));
    }

    @DisplayName("정상적으로 제거한 경우")
    @Test
    public void removeNodeTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> target = null;

        for (int i = 0; i < 5; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode.add(head, nodeToAdd, (i + 1));
            if (i % 3 == 0) {
                target = nodeToAdd;
            }
        }
        int position = ListNode.getSize(head) - 2;
        ListNode<Integer> last = head.getLast();
        int size = ListNode.getSize(head);

        // when
        ListNode<Integer> result = ListNode.remove(head, position);

        // when & then
        assertNull(result.getNext());
        assertEquals(target, result);
        assertEquals(last, head.getLast());
        assertEquals((size - 1), ListNode.getSize(head));
    }

    @DisplayName("Head node가 null일때, node를 제거한 경우")
    @Test
    public void removeToNullHeadTest() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.remove(null, 1));
    }

    @DisplayName("0 position 값을 줬을때, node를 제거한 경우")
    @Test
    public void removeZeroPositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        for (int i = 0; i < 5; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode.add(head, nodeToAdd, (i + 1));
        }
        int position = 0;

        // when
        ListNode<Integer> result = ListNode.remove(head, position);

        // then
        assertEquals(head, result);
        assertNull(head.getNext());
    }

    @DisplayName("size보다 큰 position값을 줬을때, node를 제거한 경우")
    @Test
    public void removeOverSizePositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        for (int i = 0; i < 5; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode.add(head, nodeToAdd, (i + 1));
        }
        int position = ListNode.getSize(head) + 1;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.remove(head, position));
    }

    @DisplayName("음수 position값을 줬을때, node를 추가한 경우")
    @Test
    public void removeNegativePositionTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToAdd = new ListNode<>(1);
        int position = -1;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ListNode.add(head, nodeToAdd, position));
    }

    @DisplayName("Node를 가지고 있는 경우")
    @Test
    public void containTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToCheck = new ListNode<Integer>(50);

        for (int i = 0; i < 5; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode.add(head, nodeToAdd, (i + 1));
        }
        ListNode.add(head, nodeToCheck, 6);

        // when
        boolean result = ListNode.contains(head, nodeToCheck);

        assertEquals(true, result); // 100개를 추가했으므로, 헤더 노드 포함 101개가 되어야 함.
    }

    @DisplayName("Node를 가지고 있지 않는 경우")
    @Test
    public void notContainTest() {
        // given
        ListNode<Integer> head = ListNode.createHead(0);
        ListNode<Integer> nodeToCheck = new ListNode<Integer>(50);

        for (int i = 0; i < 5; i++) {
            ListNode<Integer> nodeToAdd = new ListNode<>((i + 1));
            ListNode.add(head, nodeToAdd, (i + 1));
        }

        // when
        boolean result = ListNode.contains(head, nodeToCheck);

        assertEquals(false, result);
    }

}
