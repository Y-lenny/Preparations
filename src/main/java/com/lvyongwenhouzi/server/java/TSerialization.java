package com.lvyongwenhouzi.server.java;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Java 序列化中如果有些字段不想进行序列化，怎么办 ?
 */
public class TSerialization {

    public static void main(String[] args) {

        Person person = new Person( "1234567890");

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(person);
            oos.flush();
            // person serializable >>>>>>>>>>> �� sr ,com.lvyongwenhouzi.server.java.TSerialization$Person$�����  xp
            System.out.println("person serializable >>>>>>>>>>> " + baos.toString());
        } catch (Exception e) {
            System.out.println("exception >>>>>>>>>>>>>> " + e);
        }
        // fastjson serializable >>>>>>>>> {}
        // System.out.println("fastjson serializable >>>>>>>>> " + JSON.toJSONString(person));
    }

    @Data
    @AllArgsConstructor
    public static class Person implements Serializable {

        /**
         * static 修饰不会被序列化/反序列化
         */
        private static String username = "张三";

        /**
         * transient 修饰不会被序列化/反序列化
         */
        transient private String ident;

    }

}
