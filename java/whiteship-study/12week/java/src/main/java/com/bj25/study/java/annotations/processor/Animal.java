package com.bj25.study.java.annotations.processor;

import com.bj25.study.java.processors.DTO;
import com.bj25.study.java.processors.DTOProperty;

@DTO
public class Animal {
    @DTOProperty
    private String name;
}
