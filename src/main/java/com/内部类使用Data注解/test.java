package com.内部类使用Data注解;

public class test {
    public static void main(String[] args) {
        InnerClassDO innerClassDO = new InnerClassDO();
        InnerClassDO.InnerInner innerInner = innerClassDO.getInnerInner();
        innerInner.setAa("1");
        //Integer.MAX_VALUE
        System.out.println(innerInner.getAa());
    }
}
