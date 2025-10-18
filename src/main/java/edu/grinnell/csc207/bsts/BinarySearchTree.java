package edu.grinnell.csc207.bsts;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }


    /**
     * Constructs a new binary search tree with val.
     */
    public BinarySearchTree(Node<T> val) {
        root = val;
    }

    /**
     * @param node the root of the tree
     * @return the number of elements in the specified tree
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in this tree
     */
    public int size() {
        return sizeH(root);
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertHelper(value, root);
    }

    public Node<T> insertHelper(T value, Node<T> node) {
        if (node == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(node.value) < 0) {
                node.left = insertHelper(value, node.left);
            } else {
                node.right = insertHelper(value, node.right);
            }
            return node;
        }
    }

    ///// Part 1: Contains
   
    /**
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containsHelper(v, root);
    }

    public boolean containsHelper (T v, Node<T> node) {
        if(node == null){
            return false;
        } else if(v.compareTo(node.value) == 0){
            return true;
        } else if(v.compareTo(node.value) > 0){
            return containsHelper(v, node.right);
        } else if(v.compareTo(node.value) < 0){
            return containsHelper(v, node.left);
        } else{
            return false;
        }
    }

    ///// Part 2: Ordered Traversals

    // Codes ported from the previous lab
    public void inOrderHelper(List<T> lst, Node<T> treeRoot){
        if(treeRoot == null){
            return;
        } else if(treeRoot.left == null && treeRoot.right != null){
            lst.add(treeRoot.value);
            inOrderHelper(lst, treeRoot.right);
        }else if(treeRoot.right ==null && treeRoot.left != null){
            inOrderHelper(lst, treeRoot.left);
            lst.add(treeRoot.value); 
        }else{
            inOrderHelper(lst, treeRoot.left);
            lst.add(treeRoot.value);
            inOrderHelper(lst, treeRoot.right);
        }
    }

     /**
     * @return the (linearized) string representation of this BST
     */
    @Override
    public String toString() {
        List<T> treeElements = new ArrayList<T>(size());
        inOrderHelper(treeElements, root);
        return treeElements.toString();
    }

    /**
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        List<T> treeElements = new ArrayList<T>(size());
        inOrderHelper(treeElements, root);
        return treeElements;
    }

    ///// Part 3: BST Sorting

    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in ___ time if the tree remains balanced. 
     * 
     * Complexity analysis:
     *  isnertion: O(n log n)
     *  toList (in-order traversal): O(n)
     * -> O(n log n)
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
        BinarySearchTree<T> tree = new BinarySearchTree<T>();
        for (int i = 0; i < lst.size(); i++) {
            tree.insert(lst.get(i));
        }
        return tree.toList();
    }

    ///// Part 4: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. When the node doesn't have neither left nor right, delete it and make the root's pointer to null
     * 2. When the node only has either left or right, replace the deleted node with its single child.
     * 3. When the node has both left and right
     */

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        root = deleteHelper(value, root);
    }

    public Node<T> deleteHelper(T value, Node<T> node) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.value) < 0) {
            node.left = deleteHelper(value, node.left);
        } else if (value.compareTo(node.value) > 0) {
            node.right = deleteHelper(value, node.right);
        } else {
            // case 1
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node<T> nextRoot = node.right;
                while (nextRoot.left != null) {
                    nextRoot = nextRoot.left;
                }
                node.value = nextRoot.value;
                node.right = deleteHelper(nextRoot.value, node.right);
            }
        }
        return node;
    }
}
