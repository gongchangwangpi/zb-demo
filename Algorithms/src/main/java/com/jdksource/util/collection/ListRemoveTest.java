package com.jdksource.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List 删除常见错误
 *
 * Created by books on 2017/3/22.
 */
public class ListRemoveTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(1);list.add(2);list.add(3);
        list.add(4);list.add(5);

        List<Integer> list2 = new ArrayList<>(list);
        List<Integer> list3 = new ArrayList<>(list);
        List<Integer> list4 = new ArrayList<>(list);
        List<Integer> list5 = new ArrayList<>(list);

        for(int i = 0; i< list.size(); i++) {
            list.remove(i);
        }
        System.out.println("list1 -->>: " + list); // [2, 4]
// ----------------------------------------
        int size = list2.size();
        for(int i = 0; i < size; i++) {
            list.remove(i);
        }
        System.out.println("list2 -->>: " + list2);// Exception in thread "main" java.lang.IndexOutOfBoundsException: Index: 1, Size: 1
// ----------------------------------------
        for (Integer i: list3) {
            list3.remove(i);
        }
        System.out.println("list3 -->>: " + list3);// Exception in thread "main" java.util.ConcurrentModificationException
// ----------------------------------------
        Iterator<Integer> iterator4 = list4.iterator();
        while (iterator4.hasNext()) {
            iterator4.remove();
        }
        System.out.println("list4 -->>: " + list4);// Exception in thread "main" java.lang.IllegalStateException
// ----------------------------------------
        Iterator<Integer> iterator5 = list5.iterator();
        while (iterator5.hasNext()) {
            Integer next = iterator5.next();
            iterator5.remove();
        }
        System.out.println("list5 -->>: " + list5);// []
    }

}
