package com.bj25.study.java.annotations.processor;

import com.bj25.study.java.processors.DTO;
import com.bj25.study.java.processors.DTOProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Person
 * 
 * @author bj25
 */
@DTO(name = "PersonExampleDTO")
@NoArgsConstructor
@Getter
public class Person {

    @DTOProperty(name = "identificationId")
    private int id;

    @DTOProperty
    private String email;

    private String firstname;

    private String lastname;

    @DTOProperty
    private int age;

    private String address;

    @Builder
    public Person(int id, String email, String firstname, String lastname, int age, String address) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
    }

}
