package org.example;

import java.lang.reflect.Method;

public class temp {

//    public static void main(String... args) {
//        example(temp2::printHi);
//    }

    public static Void example(Method a) {
        Object o = new Object();
        try {
            a.invoke(o);
        } catch (Exception ignored) {
        }
        return null;
    }
}
