package com.bj25.study.java.enums;

public enum Day {
    TEST("test"), FIRST("first"), SECOND("second"), test2("TEST2");

    private String name;

    private Day(String name) {
        this.name = name;
    }
}
