package com.bj25.study.java.enums;

public enum Time implements Example {
    TEST {
        @Override
        public void method() {
            System.out.println("test");

        }
    },
    TEST2 {
        @Override
        public void method() {
            System.out.println("test2");

        }
    };

    public abstract void method();

    public static void method2() {
        System.out.println("static method");
    }

    public void printMethodEx() {
        
    }
}
