package com.example.enums;

import java.util.EnumMap;
import java.util.EnumSet;

//枚举类的集合支持，相比普通集合更加高效
//EnumMap,EnumSet
//记住，它每个只能放它声明泛型的枚举成员
public enum EnumTest4 {

    WHITE,
    BLACK;

    public static void main(String[] args) {
        EnumMap<EnumTest4,Integer> map = new EnumMap<EnumTest4, Integer>(EnumTest4.class);
//        EnumSet
    }

}
