package com.example.demo;

public class Hello {
    private int number = 10;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Hello hello = new Hello();
        hello.print();
    }

    public void print() {
        System.out.println("public Print - Number: "+this.number);
    }

    private void privatePrint() {
        System.out.println("private Print - Number: "+this.number);
    }
}