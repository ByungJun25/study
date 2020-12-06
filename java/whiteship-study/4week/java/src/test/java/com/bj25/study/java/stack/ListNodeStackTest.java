package com.bj25.study.java.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ListNodeStackTest
 */
public class ListNodeStackTest {

    @DisplayName("Push 테스트")
    @Test
    public void pushTest() {
        // given
        IStack stack = new ListNodeStack();

        // when
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }

        // then
        assertEquals(100, stack.size());
        for (int i = 0; i < stack.size(); i++) {
            assertEquals((stack.size() - 1), stack.pop());
        }
    }

    @DisplayName("Pop 정상 작동")
    @Test
    public void popTest() {
        // given
        IStack stack = new ListNodeStack();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }

        // when
        int result = stack.pop();

        // then
        assertEquals(99, result);
        assertEquals(99, stack.size());
    }

    @DisplayName("데이터 길이가 0인 stack에 pop을 사용한 경우")
    @Test
    public void popForEmptyTest() {
        // given
        IStack stack = new ListNodeStack();

        // when & then
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> stack.pop());
    }
}