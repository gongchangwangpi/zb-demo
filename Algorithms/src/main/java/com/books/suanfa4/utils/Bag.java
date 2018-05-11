package com.books.suanfa4.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 可以添加和遍历元素，不能删除，遍历时元素顺序不重要
 *
 * Created by Administrator on 2017/6/28 0028.
 */
public class Bag<E> implements Iterable<E> {

    // 储存元素
    private Set<E> elements;

    public Bag() {
        elements = new TreeSet<>();
    }

    public Bag(Set<E> elements) {
        this.elements = new TreeSet<>(elements);
    }

    public void add(E e) {
        elements.add(e);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public Iterator<E> iterator() {
        return elements.iterator();
    }

}
