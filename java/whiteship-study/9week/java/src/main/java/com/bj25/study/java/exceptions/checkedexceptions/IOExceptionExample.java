package com.bj25.study.java.exceptions.checkedexceptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * IOExceptionExample
 */
public class IOExceptionExample {

    public static void main(String[] args) {
        try (FileReader reader = new FileReader("invalidFileName")) {

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException 발생");
        } catch (IOException e) {
            System.out.println("IOException 발생");
        }
    }

}