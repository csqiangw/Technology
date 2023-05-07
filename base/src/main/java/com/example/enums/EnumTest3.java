package com.example.enums;

//枚举的常用方法
//枚举默认继承自Enum，所以无法再次继承，但也可以实现接口，实现方法
public enum EnumTest3 implements EnumInterface{

    WHITE,
    BLACK;

    @Override
    public void test() {

    }

    //可以有成员变量
    public static String a = "a";
    public String b = "b";

    //可以有成员方法
    public void enumMethod(){

    }

    public static void enumMethod1(){

    }

    public static void main(String[] args) {
        //values 获取所有枚举变量
        EnumTest3[] values = EnumTest3.values();

        //valueOf根据字符串，转化为枚举变量
        EnumTest3 white = EnumTest3.valueOf("WHITE");
        EnumTest3 black = EnumTest3.valueOf("BLACK");

        //compareTo比较两个枚举成员声明的位置
        white.compareTo(white);

        //ordinal获取枚举成员的索引位置
        black.ordinal();
    }

}
