package com.books.dataStructrue.d4;

/**
 * 二叉查找树
 * 
 * @author zhangbo
 */
public class BinarySearchTree<T extends Comparable<? super T>> {
    
    private Node<T> root;
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void insert(T element) {
        root = insert(element, root);
    }

    private Node<T> insert(T element, Node<T> t) {
        if (element == null) {
            throw new NullPointerException("null element");
        }
        if (t == null) {
            return new Node<>(element);
        }

        int compareResult = element.compareTo(t.element);
        
        if (compareResult > 0) {
            t.right = insert(element, t.right);
        } else if (compareResult < 0) {
            t.left = insert(element, t.left);
        } else {
            // ignore
        }
        return t;
    }
    
    public void clear() {
        root = null;
    }

    public boolean contains(T element) {
        if (element == null) {
            return false;
        }
        if (root == null) {
            return false;
        }
        return contains(element, root);
    }

    private boolean contains(T element, Node<T> t) {
        if (t == null) {
            return false;
        }
        int compareResult = element.compareTo(t.element);

        if (compareResult > 0) {
            return contains(element, t.right);
        } else if (compareResult < 0) {
            return contains(element, t.left);
        } else {
            return true;
        }
    }
    
    public T findMin() {
        Node<T> min = findMin(root);
        return min == null ? null : min.element;
    }

    private Node<T> findMin(Node<T> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        } else {
            return findMin(t.left);
        }
    }
    
    public T findMax() {
        Node<T> max = findMax(root);
        return max == null ? null : max.element;
    }

    private Node<T> findMax(Node<T> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }

    @Override
    public String toString() {
        return "BinarySearchTree{" +
                "root=" + root +
                '}';
    }

    /**
     * 内部类
     * 
     * @param <T>
     */
    private static class Node<T> {
        
        private T element;

        private Node<T> left;

        private Node<T> right;

        public Node(T element) {
            this(element, null, null);
        }

        public Node(T element, Node left, Node right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
    
}
