package com.bj25.study.java.enums;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Day, String> enumMap = new EnumMap<>(Day.class);
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("String", 1);
        hashMap.put("String2", 2);
        hashMap.put("String3", 3);
        hashMap.put("String4", 4);
        hashMap.put("String5", 5);

        System.out.println(hashMap);

        enumMap.put(Day.FIRST, "first");
        enumMap.put(Day.TEST, "test");
        enumMap.put(Day.SECOND, "test");
        
        System.out.println(enumMap);

        System.out.println(Day.FIRST.name());
        System.out.println(Day.TEST.ordinal());
        System.out.println(Day.TEST.compareTo(Day.FIRST));
        System.out.println(Day.FIRST.getClass());
        //System.out.println(Day.valueOf("TEST2").name());
        //Arrays.stream(Day.values()).forEach(d -> System.out.println(d.name()));
    }
}
