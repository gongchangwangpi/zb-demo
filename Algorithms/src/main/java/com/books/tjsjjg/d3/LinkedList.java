package com.books.tjsjjg.d3;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class LinkedList<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public void add(T item) {
        Node<T> node = new Node<>(item);
        if (first == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        size++ ;
    }

    public void set(int index, T item) {
        if (index < 0) {
            throw new IllegalArgumentException("index must >= 0, index: " + index);
        }
        if (index >= size) {
            throw new IllegalArgumentException("outOfBound, index: " + index);
        }
        Node<T> node = new Node<>(item);
        if (index == 0) {
            Node<T> fi = first;
            first = node;
            first.next = fi == null ? null : fi.next;
            return;
        }
        int count = 0;
        Node<T> current = first;
        while (current != null) {
            if (count == index - 1) {
                Node<T> next = current.next;
                current.next = node;
                node.next = next.next;
                return;
            }
            current = current.next;
            count ++;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        Node current = first;
        String s = "";
        while (current != null) {
            s += current.item + ", ";
            current = current.next;
        }
        return s == "" ? null : s;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }

}
