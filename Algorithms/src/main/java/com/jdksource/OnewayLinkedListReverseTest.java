package com.jdksource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 单向链表逆序
 * 
 * @author zhangbo
 */
public class OnewayLinkedListReverseTest {

    public static void main(String[] args) {

        OnewayLinkedList<String> list = new OnewayLinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        System.out.println(list);
        
        list.reverse();
        System.out.println(list);
        
        list.add("6");
        System.out.println(list);
    }
    
    public static class OnewayLinkedList<E> {
        
        Node<E> head;
        Node<E> tail;
        
        public void add(E value) {
            if (head == null) {
                head = tail = new Node<>(value, null);
            } else {
                /*Node n = head;
                while (n.next != null) {
                    n = n.next;
                }
                n.next = new Node<>(value, null);*/
                Node<E> node = new Node<>(value, null);
                tail.next = node;
                tail = node;
            }
        }
        
        public void reverse() {
            if (head == null || head.next == null) {
                return;
            }
            Node<E> prev, middle, next;
            prev = head;
            tail = head;
            middle = head.next;
            while (middle != null) {
                next = middle.next;
                // 将指针倒转
                middle.next = prev;
                // 将临时变量依次向后移动
                prev = middle;
                middle = next;
            }
            head.next = null;
            head = prev;
        }

        @Override
        public String toString() {
            if (head == null) {
                return "empty";
            } 
            StringBuilder sb = new StringBuilder();
            Node n = head;
            while (n != null) {
                sb.append(n.value).append(", ");
                n = n.next;
            }
            return sb.toString();
        }

        @NoArgsConstructor
        @AllArgsConstructor
        static class Node<E> {
            E value;
            Node<E> next;
        }
    }
}
