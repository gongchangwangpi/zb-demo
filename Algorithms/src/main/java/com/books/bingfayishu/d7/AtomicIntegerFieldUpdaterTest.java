package com.books.bingfayishu.d7;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author zhangbo
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) {

        User zhangsan = new User("zhangsan", 17);

        int age = updater.getAndIncrement(zhangsan);

        System.out.println(age);

        System.out.println(updater.get(zhangsan));

    }
    
    private static final AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
    
    static class User {
        private String name;
        public volatile int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
