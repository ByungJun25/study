package com.bj25.study.java.interfaces;

public class Test1 implements Test4 {
    public static void main(String[] args) {
        Test1 test = new Test1();

        test.method(0);
    }

    @Override
    public void otherMethod() {
    }

    @Override
    public void method() {
    }
}
