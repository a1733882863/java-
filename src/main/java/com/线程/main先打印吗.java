package com.线程;

public class main先打印吗 {


    public static void main(String[] args) {

        int n = 1000;
        while (n--!=0){
            new Thread(()->
                    System.out.println("new thread")
            ).start();
        }
        System.out.println("main");
    }
}
