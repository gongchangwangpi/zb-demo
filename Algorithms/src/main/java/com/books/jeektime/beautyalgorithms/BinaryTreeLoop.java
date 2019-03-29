package com.books.jeektime.beautyalgorithms;

import com.books.dataStructrue.d4.BinarySearchTree;

/**
 * @author zhangbo
 */
public class BinaryTreeLoop {

    public static void main(String[] args) {

        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.insert("1");
        tree.insert("2");
        tree.insert("3");
        tree.insert("4");
        tree.insert("5");
        
        BinarySearchTree.printPreOrder(tree.getRoot());
        BinarySearchTree.printInOrder(tree.getRoot());
        BinarySearchTree.printPostOrder(tree.getRoot());
        
    }
    
}
