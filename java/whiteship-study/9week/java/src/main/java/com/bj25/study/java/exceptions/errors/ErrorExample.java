package com.bj25.study.java.exceptions.errors;

public class ErrorExample {

    public static void main(String[] args) {
        ErrorExample.infiniteLoop(0); // java.lang.StackOverflowError 발생
    }

    public static void infiniteLoop(int number) {
        infiniteLoop(number++);
    }

}
