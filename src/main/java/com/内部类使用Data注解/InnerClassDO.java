package com.内部类使用Data注解;

import lombok.Data;

@Data
public class InnerClassDO {
    private String a;
    private String b;
    InnerInner innerInner;

    @Data
    public  static class InnerInner {
        private String aa;
        private String bb;
    }
}
