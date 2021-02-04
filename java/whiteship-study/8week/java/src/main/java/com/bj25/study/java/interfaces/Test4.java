package com.bj25.study.java.interfaces;

public interface Test4 extends Test5 {
    int number = 5;

    default int method(int n) {
        if (n == -1) {
            return 0;
        }
        n += 1;
        if (n > 5)
            return -1;
        System.out.println("Test4 - method");
        return method(n);
    }

    void otherMethod();

    @Override
    default void print() {
        Test5.super.print();
        System.out.println("OtherInterface default method");
    }

    @SuppressWarnings("unused")
    private void privateMethod() {
        Test5.staticMethod();
    }

    static void staticMethod() {
        System.out.println("MyInterface static method!");
    }
}
