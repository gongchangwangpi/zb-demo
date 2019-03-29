package com.books.jeektime.beautyalgorithms;


import java.util.HashSet;
import java.util.Set;

/**
 * 检测链表中是否存在还
 * 
 * 快慢指针法和遍历足迹法
 * 
 * 
 * @author zhangbo
 */
public class LinkedListDetectCycleTest {


    public static void main(String[] args) {

        SingleNode<String> singleNode1 = new SingleNode<>("1");
        SingleNode<String> singleNode2 = new SingleNode<>("2");
        SingleNode<String> singleNode3 = new SingleNode<>("3");
        SingleNode<String> singleNode4 = new SingleNode<>("4");
        SingleNode<String> singleNode5 = new SingleNode<>("5");
        
        singleNode1.next = singleNode2;
        singleNode2.next = singleNode3;
        singleNode3.next = singleNode4;
        singleNode4.next = singleNode5;
//        singleNode5.next = singleNode3;

        System.out.println(hasLoop1(singleNode1));
        System.out.println(hasLoop2(singleNode1));
        
    }

    /**
     * 快慢指针
     * 在一次遍历中，同时使用两个指针遍历，
     * 慢指针一个一个的一次遍历，快指针一次遍历两个数据
     * 这样快指针正常情况下会先结束遍历到达链表尾部，即不存在环
     * 如果存在环，则快慢指针会先后进入环中，并在某个位置相遇，即快慢指针相等时，存在环
     * 
     * @param node
     * @return
     */
    public static boolean hasLoop1(SingleNode node) {
        if (node == null) {
            return false;
        }
        
        SingleNode slow = node, quick = slow.next;
        while (quick != null && quick.hasNext()) {
            slow = slow.next;
            if (slow == null) {
                return false;
            }
            quick = quick.next.next;
            if (quick == null) {
                return false;
            } else if (quick == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 足迹遍历法
     * 将每个遍历后的数据保存起来，如果后面遇到有一样的数据，则存在环
     * 
     * @param node
     * @return
     */
    static Set set = new HashSet<>();
    // FIXME 还没完成
    public static boolean hasLoop2(SingleNode node) {
        if (node == null) {
            return false;
        }
        if (!set.add(node.data)) {
            return true;
        } else {
            return hasLoop2(node.next);
        }
    }
    
    
    private static class SingleNode<T> {
        public SingleNode<T> next;
        public T data;

        public SingleNode(T data) {
            this.data = data;
        }

        public T next() {
            return next != null ? next.data : null;
        }
        
        public boolean hasNext() {
            return next != null;
        }
    }
}
