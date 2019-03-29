package com.books.jeektime.beautyalgorithms;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangbo
 */
public class ArrayStack {
    
    private Object[] items;
    private int count;
    private int size;
    
    public ArrayStack(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size must bigger 1");
        }
        this.items = new Object[size];
        this.size = size;
    }
    
    public boolean push(Object o) {
        if (count == size) {
            // 已满，直接返回false
            return false;
        }
        items[count++] = o;
        return true;
    }
    
    public Object pop() {
        if (count == 0) {
            return null;
        }
        count--;
        Object item = items[count];
        items[count] = null;
        return item;
    }
    
    public void print() {
        System.out.println("size = " + size + ", count = " + count + ", items = " + JSON.toJSONString(items));
    }

    public static void main(String[] args) {

        ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);

        arrayStack.print();

        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());

        arrayStack.print();
        
        arrayStack.push(4);
        arrayStack.print();

    }
}
