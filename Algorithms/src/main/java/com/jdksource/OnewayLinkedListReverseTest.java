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
    }
    
    static class OnewayLinkedList<E> {
        
        Node<E> head;
        Node<E> tail;
        
        void add(E value) {
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
        
        void reverse() {
            if (head == null || head.next == null) {
                return;
            }
            Node<E> p1, p2, p3;
            p1 = head;
            p2 = head.next;
            while (p2 != null) {
                p3 = p2.next;
                // 将指针倒转
                p2.next = p1;
                // 将临时变量依次向后移动
                p1 = p2;
                p2 = p3;
            }
            head.next = null;
            head = p1;
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
