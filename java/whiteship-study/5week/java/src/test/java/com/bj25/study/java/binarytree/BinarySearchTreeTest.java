package com.bj25.study.java.binarytree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.bj25.study.java.binarytree.model.BinarySearchTree;
import com.bj25.study.java.binarytree.model.Node;
import com.bj25.study.java.binarytree.model.Tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("Binary Search Tree 테스트 클래스")
public class BinarySearchTreeTest {

    @DisplayName("Binary Search Tree 생성 테스트 - root값이 주어지지 않았을때")
    @Test
    void create_BinarySearchTree_Without_Init_Value_Test() {
        // when
        BinarySearchTree bst = new BinarySearchTree();

        // then
        assertNull(bst.getRoot());
    }

    @DisplayName("Binary Search Tree 생성 테스트 - root값이 주어졌을때")
    @Test
    void create_BinarySearchTree_With_Init_Value_Test() {
        // when
        BinarySearchTree bst = new BinarySearchTree(1);

        // then
        assertNotNull(bst.getRoot());
        assertEquals(1, bst.getRoot().getData());
        assertFalse(bst.getRoot().hasLeft());
        assertFalse(bst.getRoot().hasRight());
    }

    @DisplayName("Binary Search Tree의 기능 테스트 - root값이 주어지지 않은 트리 생성시")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class BinarySearchTreeFunctionWithoutInitValueTest {
        BinarySearchTree bst = new BinarySearchTree();
        Set<Integer> containedValues = new HashSet<>();

        @DisplayName("데이터 추가(add) 테스트 - 중복 없는 데이터의 경우")
        @Test
        @Order(0)
        void add_Test() {
            // when
            for (int i = 0; i < 100; i++) {
                int data = generateRandom();
                bst.add(data);
                containedValues.add(data);
            }

            // then
            assertNotEquals(0, bst.size());
            assertEquals(containedValues.size(), bst.size());
        }

        @DisplayName("데이터 삭제(remove) 테스트 - 존재하지 않는 데이터의 경우 - 아무일 없음")
        @Test
        @Order(1)
        void remove_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            bst.remove(data);

            // then
            assertEquals(containedValues.size(), bst.size());
        }

        @DisplayName("데이터 삭제(remove) 테스트 - 존재하는 데이터의 경우 - 데이터 삭제")
        @Test
        @Order(2)
        void remove_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target removed data: " + data);
            containedValues.remove(data);

            // when
            bst.remove(data);

            // then
            assertEquals(containedValues.size(), bst.size());
            assertEquals(false, bst.contain(data));
        }

        @DisplayName("데이터 삭제(delete) 테스트 - 존재하지 않는 데이터의 경우 - NoSuchElementException 발생")
        @Test
        @Order(3)
        void delete_With_Not_Contained_Value_Test() {
            // then
            assertThrows(NoSuchElementException.class, () -> {
                // given
                int data = generateRandom();

                while (containedValues.contains(data)) {
                    data = generateRandom();
                }

                // when
                bst.delete(data);
            });
        }

        @DisplayName("데이터 삭제(delete) 테스트 - 존재하는 데이터의 경우 - 데이터 삭제")
        @Test
        @Order(4)
        void delete_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target deleted data: " + data);
            containedValues.remove(data);

            // when
            bst.delete(data);

            // then
            assertEquals(containedValues.size(), bst.size());
            assertEquals(false, bst.contain(data));
        }

        @DisplayName("데이터 조회(element) 테스트 - 존재하지 않는 데이터 요구 - null 반환")
        @Test
        @Order(5)
        void element_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            Node node = bst.element(data);

            // then
            assertNull(node);
        }

        @DisplayName("데이터 조회(element) 테스트 - 존재하는 데이터 요구 - 요구한 데이터를 가진 노드 반환")
        @Test
        @Order(6)
        void element_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            Node node = bst.element(data);

            // then
            assertNotNull(node);
            assertEquals(data, node.getData());
        }

        @DisplayName("데이터 조회(peek) 테스트 - 존재하지 않는 데이터 요구 - NoSuchElementException 발생")
        @Test
        @Order(7)
        void peek_With_Not_Contained_Value_Test() {
            // then
            assertThrows(NoSuchElementException.class, () -> {
                // given
                int data = generateRandom();

                while (containedValues.contains(data)) {
                    data = generateRandom();
                }

                // when
                bst.peek(data);
            });
        }

        @DisplayName("데이터 조회(peek) 테스트 - 존재하는 데이터 요구 - 요구한 데이터를 가진 노드 반환")
        @Test
        @Order(8)
        void peek_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            Node node = bst.peek(data);

            // then
            assertNotNull(node);
            assertEquals(data, node.getData());
        }

        @DisplayName("데이터 존재 여부 확인(contain) 테스트 - 존재하지 않는 데이터일 경우 - false 반환")
        @Test
        @Order(9)
        void conain_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            boolean result = bst.contain(data);

            // then
            assertFalse(result);
        }

        @DisplayName("데이터 존재 여부 확인(contain) 테스트 - 존재하는 데이터일 경우 - true 반환")
        @Test
        @Order(10)
        void conain_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            boolean result = bst.contain(data);

            // then
            assertTrue(result);
        }

    }

    @DisplayName("Binary Search Tree의 기능 테스트 - root값이 주어진 트리 생성시")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class BinarySearchTreeFunctionWithInitValueTest {
        BinarySearchTree bst;
        Set<Integer> containedValues = new HashSet<>();

        {
            int data = generateRandom();
            bst = new BinarySearchTree(generateRandom());
            containedValues.add(data);
        }

        @DisplayName("데이터 추가(add) 테스트 - 중복 없는 데이터의 경우")
        @Test
        @Order(0)
        void add_Test() {
            // when
            for (int i = 0; i < 99; i++) {
                int data = generateRandom();
                bst.add(data);
                containedValues.add(data);
            }

            // then
            assertNotEquals(0, bst.size());
            assertEquals(containedValues.size(), bst.size());
        }

        @DisplayName("데이터 삭제(remove) 테스트 - 존재하지 않는 데이터의 경우 - 아무일 없음")
        @Test
        @Order(1)
        void remove_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            bst.remove(data);

            // then
            assertEquals(containedValues.size(), bst.size());
        }

        @DisplayName("데이터 삭제(remove) 테스트 - 존재하는 데이터의 경우 - 데이터 삭제")
        @Test
        @Order(2)
        void remove_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target removed data: " + data);
            containedValues.remove(data);

            // when
            bst.remove(data);

            // then
            assertEquals(containedValues.size(), bst.size());
            assertEquals(false, bst.contain(data));
        }

        @DisplayName("데이터 삭제(delete) 테스트 - 존재하지 않는 데이터의 경우 - NoSuchElementException 발생")
        @Test
        @Order(3)
        void delete_With_Not_Contained_Value_Test() {
            // then
            assertThrows(NoSuchElementException.class, () -> {
                // given
                int data = generateRandom();

                while (containedValues.contains(data)) {
                    data = generateRandom();
                }

                // when
                bst.delete(data);
            });
        }

        @DisplayName("데이터 삭제(delete) 테스트 - 존재하는 데이터의 경우 - 데이터 삭제")
        @Test
        @Order(4)
        void delete_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target deleted data: " + data);
            containedValues.remove(data);

            // when
            bst.delete(data);

            // then
            assertEquals(containedValues.size(), bst.size());
            assertEquals(false, bst.contain(data));
        }

        @DisplayName("데이터 조회(element) 테스트 - 존재하지 않는 데이터 요구 - null 반환")
        @Test
        @Order(5)
        void element_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            Node node = bst.element(data);

            // then
            assertNull(node);
        }

        @DisplayName("데이터 조회(element) 테스트 - 존재하는 데이터 요구 - 요구한 데이터를 가진 노드 반환")
        @Test
        @Order(6)
        void element_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            Node node = bst.element(data);

            // then
            assertNotNull(node);
            assertEquals(data, node.getData());
        }

        @DisplayName("데이터 조회(peek) 테스트 - 존재하지 않는 데이터 요구 - NoSuchElementException 발생")
        @Test
        @Order(7)
        void peek_With_Not_Contained_Value_Test() {
            // then
            assertThrows(NoSuchElementException.class, () -> {
                // given
                int data = generateRandom();

                while (containedValues.contains(data)) {
                    data = generateRandom();
                }

                // when
                bst.peek(data);
            });
        }

        @DisplayName("데이터 조회(peek) 테스트 - 존재하는 데이터 요구 - 요구한 데이터를 가진 노드 반환")
        @Test
        @Order(8)
        void peek_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            Node node = bst.peek(data);

            // then
            assertNotNull(node);
            assertEquals(data, node.getData());
        }

        @DisplayName("데이터 존재 여부 확인(contain) 테스트 - 존재하지 않는 데이터일 경우 - false 반환")
        @Test
        @Order(9)
        void conain_With_Not_Contained_Value_Test() {
            // given
            int data = generateRandom();

            while (containedValues.contains(data)) {
                data = generateRandom();
            }

            // when
            boolean result = bst.contain(data);

            // then
            assertFalse(result);
        }

        @DisplayName("데이터 존재 여부 확인(contain) 테스트 - 존재하는 데이터일 경우 - true 반환")
        @Test
        @Order(10)
        void conain_With_Contained_Value_Test() {
            // given
            int data = containedValues.stream().findAny().orElseThrow(IllegalStateException::new);

            System.out.println("target searched data: " + data);
            containedValues.remove(data);

            // when
            boolean result = bst.contain(data);

            // then
            assertTrue(result);
        }

    }

    @DisplayName("Binary Search Tree 탐색 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class BinarySearchTreeTravelTest {
        BinarySearchTree bst = new BinarySearchTree();
        Set<Integer> containedValues = new HashSet<>();

        @DisplayName("데이터 초기화")
        @Test
        @Order(0)
        void init() {
            for (int i = 0; i < 100; i++) {
                int data = generateRandom(150);
                if(!containedValues.contains(data)) {
                    bst.add(data);
                    containedValues.add(data);
                }
            }
            System.out.println("넣은 데이터 순서");
            System.out.println(containedValues);
            System.out.println();
        }

        @DisplayName("너비 우선 탐색 테스트")
        @Test
        @Order(1)
        void bfs_test() {
            // when
            Tree.bfs(bst.getRoot());

            // then

        }

        @DisplayName("깊이 우선 탐색 테스트 - preOrder")
        @Test
        @Order(2)
        void dfs_preOrder_test() {
            // when
            Tree.dfs(bst.getRoot(), com.bj25.study.java.binarytree.enums.Order.PRE);

            // then
            
        }

        @DisplayName("깊이 우선 탐색 테스트 - inOrder")
        @Test
        @Order(3)
        void dfs_inOrder_test() {
            // when
            Tree.dfs(bst.getRoot(), com.bj25.study.java.binarytree.enums.Order.IN);

            // then
            
        }

        @DisplayName("깊이 우선 탐색 테스트 - postOrder")
        @Test
        @Order(4)
        void dfs_postOrder_test() {
            // when
            Tree.dfs(bst.getRoot(), com.bj25.study.java.binarytree.enums.Order.POST);

            // then
            
        }

    }

    private int generateRandom() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    private int generateRandom(int limit) {
        return ThreadLocalRandom.current().nextInt(1, limit);
    }

}
