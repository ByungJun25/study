package com.bj25.study.java.exceptions.checkedexceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ParseExceptionExample
 */
public class ParseExceptionExample {

    public static void main(String[] args) {
        try {
            new SimpleDateFormat("MM-dd-yyyy").parse("test");
        } catch (ParseException e) {
            System.out.println("ParseException 발생");
        }
    }

}