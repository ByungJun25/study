package com.bj25.study.java.annotations;

/**
 * Main
 * 
 * @author bj25
 */
@Study(topic = "annotations", types = "java")
public class Main {

    public static void main(String[] args) {
        System.out.println("test");
    }

    @Deprecated
    public void demethod() {
    }

    @SuppressWarnings("test")
    public void method() {

    }

    @Schedule(day = Day.FRI)
    @Schedule(day = Day.MON)
    public void scheduledTask() {

    }
}