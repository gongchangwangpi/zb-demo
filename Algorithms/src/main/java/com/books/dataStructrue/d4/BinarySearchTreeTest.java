package com.books.dataStructrue.d4;

/**
 * @author zhangbo
 */
public class BinarySearchTreeTest {

    public static void main(String[] args) {

        BinarySearchTree<String> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.insert("5");
        binarySearchTree.insert("4");
        binarySearchTree.insert("2");
        binarySearchTree.insert("6");
        binarySearchTree.insert("7");

        System.out.println(binarySearchTree);
        
//        System.out.println(binarySearchTree.contains("3"));
        System.out.println(binarySearchTree.contains("7"));
        
        System.out.println(binarySearchTree.findMin());
        System.out.println(binarySearchTree.findMax());
        
    }
    
}
