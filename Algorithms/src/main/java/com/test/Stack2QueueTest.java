package com.test;

import java.util.Stack;

/**
 * 利用2个栈，实现一个队列
 * 
 * @author zhangbo
 */
public class Stack2QueueTest {

    public static void main(String[] args) {

        Stack2Queue<String> stack2Queue = new Stack2Queue<>();
        
        stack2Queue.push("1");
        stack2Queue.push("2");
        stack2Queue.push("3");

        System.out.println(stack2Queue.pop());

        stack2Queue.push("4");
        stack2Queue.push("5");
        System.out.println(stack2Queue.pop());
        System.out.println(stack2Queue.pop());
        System.out.println(stack2Queue.pop());
        System.out.println(stack2Queue.pop());
        
    }

    /**
     * 栈 FILO
     * 队列 FIFO
     * 
     * @param <E>
     */
    static class Stack2Queue<E> {
        
        private Stack<E> original = new Stack<>();
        private Stack<E> backup = new Stack<>();
        
        public E pop() {
            if (backup.isEmpty()) {
                // 只有当备份栈中为空时，才从原始栈中复制，
                // 保证了每次复制后，备份栈中都是保持了队列的顺序
                while (!original.isEmpty()) {
                    backup.push(original.pop());
                }
            }
            return backup.pop();
        }
        
        public void push(E e) {
            // 始终压入原始栈中
            original.push(e);
        }
        
    }
}
