package com.jdksource.util.concurrent;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangbo
 */
public class VolatileObjArrayTest {
    
    private static volatile User[] arr = new User[5];

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            arr[i] = new User("name" + i, i);
        }
        
        Thread writer = new Thread(new Writer(5));
        Thread reader = new Thread(new Reader());
        
        writer.start();
        reader.start();
    }
    
    static class Reader implements Runnable {

        @Override
        public void run() {
            System.out.println(JSON.toJSONString(arr));
        }
    }
    
    static class Writer implements Runnable {
        int i;

        public Writer(int i) {
            this.i = i;
        }
        
        @Override
        public void run() {
            for (int j = i; j < i + 5; j++) {
                User user = arr[j - i];
                user.setName(String.valueOf(j));
                user.setAge(j);
            }    
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private int age;
    }
}
