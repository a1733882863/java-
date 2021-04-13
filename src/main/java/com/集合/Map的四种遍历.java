package com.集合;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Map的四种遍历 {

    // 构建一个Map 初始值为3条数据
    Map<Integer, String> map = new HashMap<Integer, String>();

    public Map的四种遍历() {
        long start = System.currentTimeMillis();

        int i = 10000000;
        while(i != 0){
            map.put(i,"你好"+i);
            i--;

        }
        long end = System.currentTimeMillis();
        long time = end - start;
        //System.out.println(time);
    }

    //第一种：普通遍历，二次取值,放入一个set里面
    public void method1() {
        System.out.print("通过Map.keySet遍历key和value：");
        long start = System.currentTimeMillis();

        for(int key : map.keySet()){
            //System.out.println("key:" + key +" value:" + map.get(key));
            map.get(key);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
    }

    //第二种: 通过Iterator迭代器循环遍历Map.entrySet().iterator();
    public void method2() {
        System.out.print("通过Map.entrySet使用iterator遍历key和value：");
        long start = System.currentTimeMillis();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
            //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            entry.getKey();
            entry.getValue();
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);

    }
    //第三种：笔者推荐，尤其是容量大时(相对来说 比2好一点 效率高)
    public void method3() {
        System.out.print("通过Map.entrySet遍历key和value");
        long start = System.currentTimeMillis();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            entry.getKey();
            entry.getValue();
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
    }

    public void method4() {
        //第四种
        System.out.print("通过Map.values()遍历所有的value，但不能遍历key");
        long start = System.currentTimeMillis();

        for (String v : map.values()) {
            //System.out.println("value= " + v);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
    }


    public static void main(String[] args) {
        new Map的四种遍历().method1();
        new Map的四种遍历().method2();
        new Map的四种遍历().method3();
        new Map的四种遍历().method4();

    }
}

