package com.redis;

public class Student {
    public String name;
    private String sex;
    public int age;
    public boolean isGraduate;
    public String home;

    @Override
    public String toString() {
        return "Student {" + "name:'" + name + "',"
                + "sex:'" + sex + "',"
                + "age:" + age + ","
                + "isGraduate:'" + isGraduate + "',"
                + "home:'" + home + "'}";
    }
}
