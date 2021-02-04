package com.bj25.study.java.demo;

import com.bj25.study.java.annotations.processor.PersonExampleDTO;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        PersonExampleDTO personDTO = PersonExampleDTO.builder().age(10).email("email@email.com").identificationId(1).build();

        System.out.println("Age: "+personDTO.getAge());
        System.out.println("email: "+personDTO.getEmail());
        System.out.println("id: "+personDTO.getIdentificationId());
    }
}