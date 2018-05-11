package com.jdksource.util.collection;

import java.util.EnumSet;
import java.util.Iterator;

/**
 *
 *
 * Created by books on 2017/4/7.
 */
public class EnumSetTest {

    public static void main(String[] args) {

        EnumSet<E> enumSet = EnumSet.allOf(E.class);

        EnumSet.noneOf(E.class);
        

        boolean empty = enumSet.isEmpty();

        System.out.println(empty);

        Iterator<E> iterator = enumSet.iterator();
        
        while (iterator.hasNext()) {
            E next = iterator.next();
            System.out.println(next);
        }

    }

}

enum E {

    E1,
    E2

}