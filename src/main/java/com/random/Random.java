package com.random;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static void main(String[] args) {
        int i = RandomUtil.randomInt(100);
        System.out.println(i);
        System.out.println();
    }
}
