package com.bj25.study.java.interfaces;

public interface Test5 {
    void method();

    default void print() {
        System.out.println("MyInterface default method");
    }

    @SuppressWarnings("unused")
    private void privateMethod() {
        System.out.println("MyInterface private method!");
    }

    static void staticMethod() {
        System.out.println("MyInterface static method!");
    }
}
