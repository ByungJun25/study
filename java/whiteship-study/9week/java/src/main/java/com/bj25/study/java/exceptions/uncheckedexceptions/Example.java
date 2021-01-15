package com.bj25.study.java.exceptions.uncheckedexceptions;

import java.util.ArrayList;
import java.util.ListIterator;

public class Example {

    public static void main(String[] args) throws InterruptedException {
        Example.nullPointerExceptionExample();
        // Example.arrayIndexOutOfBoundsExceptionExample();
        // Example.stringIndexOutOfBoundsExceptionExample();
        // Example.numberFormatExceptionExample();
        // Example.arithmeticExceptionExample();
        // Example.classCastExceptionExample();
        // Example.illegalArgumentExceptionExample();
        // Example.illegalStateExceptionExample();
    }

    @SuppressWarnings("null")
    public static void nullPointerExceptionExample() {
        String text = null;

        text.length(); // nullPointerException 발생
    }

    @SuppressWarnings("unused")
    public static void arrayIndexOutOfBoundsExceptionExample() {
        int[] numbers = new int[5];
        int number = numbers[5]; // arrayIndexOutOfBoundsException 발생
    }

    public static void stringIndexOutOfBoundsExceptionExample() {
        String text = "Test text";
        text.charAt(9); // stringIndexOutOfBoundsException 발생
    }

    @SuppressWarnings("unused")
    public static void numberFormatExceptionExample() {
        int number = Integer.parseInt("text"); // numberFormatException 발생
    }

    @SuppressWarnings("unused")
    public static void arithmeticExceptionExample() {
        int num1 = 3;
        int num2 = 0;
        int result = num1 / num2; // arithmeticException 발생
    }

    @SuppressWarnings("unused")
    public static void classCastExceptionExample() {
        Parent child = new Child();
        Child2 child2 = (Child2) child; // ClassCastException 발생
    }

    public static class Child extends Parent {

    }

    public static class Child2 extends Parent {

    }

    public static class Parent {

    }

    /**
     * 
     * @throws InterruptedException
     */
    public static void illegalArgumentExceptionExample() throws InterruptedException {
        Thread.sleep(-1); // illegalArgumentException 발생
    }

    /**
     * 
     * @exception IllegalStateException text
     */
    public static void illegalStateExceptionExample() {
        ListIterator<Object> it = new ArrayList<>().listIterator();
        it.remove(); // illegalStateException 발생
    }

}
