package com.books.suanfa4.d1._3;

import com.books.suanfa4.utils.Bag;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
public class BagTest {

    public static void main(String[] args) {

        Bag<Integer> bag = new Bag<>();
        bag.add(2);
        bag.add(6);
        bag.add(3);

        for (Integer i: bag) {
            System.out.println(i);
        }

        Iterator<Integer> iterator = bag.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }

    }

}
