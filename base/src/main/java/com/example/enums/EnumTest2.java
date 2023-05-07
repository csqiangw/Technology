package com.example.enums;

import lombok.Getter;

//带参数的枚举，里面要声明属性（getter方法，因为不需要设置），以及构造方法(private)
public enum EnumTest2 {

    HOHAI("河海大学"),

    JINKE("金陵科技学院");

    @Getter
    private String name;

    private EnumTest2(String name){
        this.name = name;
    }

}
