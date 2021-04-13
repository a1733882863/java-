package com.redis;


import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import static java.lang.Thread.sleep;

//FastJSON进行对象的序列化和反序列化
public class page17 {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("101.37.31.239",6380);
        jedis.set("a", String.valueOf(123));
        System.out.println(jedis.get("a"));
        Student student = new Student();
        student.age = 11;
        student.name = "李雷";

        String s = JSONObject.toJSONString(student);
        System.out.println("转换成json字符串并存入redis"+s);
        jedis.set("student1",s);
        jedis.expire("student1",1);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ss = jedis.get("student1");
        System.out.println("过期时间之前取到redis里的数据"+ss);

        Student sss = JSONObject.parseObject(ss,Student.class);
        System.out.println("反序列化后的值"+sss);

    }

}
