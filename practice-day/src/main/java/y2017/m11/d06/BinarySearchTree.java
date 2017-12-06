package y2017.m11.d06;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/6
 */
public class BinarySearchTree<T> {
    private Node<T> root;

    private int size;

    private void add(T t) {
        size++;
        Node<T> node = new Node<>(t);
        if (root == null) {
            root = node;
        } else {
            doAdd(root, node);
        }
    }

    private void doAdd(Node<T> root, Node<T> node) {
        if (root.compareTo(node.t) > 0) {   // left node
            Node<T> leftNode = root.leftNode;
            if (leftNode == null) {
                root.leftNode = node;
            } else {
                doAdd(root.leftNode, node);
            }
        } else {                            // right node
            Node<T> rightNode = root.rightNode;
            if (rightNode == null) {
                root.rightNode = node;
            } else {
                doAdd(root.rightNode, node);
            }
        }
    }

    private void remove(T t) {

    }

    private int size() {
        return size;
    }

    private int deep() {
        return doDeep(root);
    }

    private int doDeep(Node<T> node) {
        int deep = 0;
        if (node != null) {
            deep++;
        } else {
            return deep;
        }

        int leftDeep = doDeep(node.leftNode);
        int rightDeep = doDeep(node.rightNode);
        return deep + (leftDeep > rightDeep ? leftDeep : rightDeep);
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(4);
        tree.add(2);
        tree.add(3);
        tree.add(1);
        tree.add(8);
        tree.add(5);
        System.out.println(tree);
        System.out.println(tree.size());
        System.out.println(tree.deep());
    }

    @Override
    public String toString() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            Node<T> node = queue.poll();
        }
        return super.toString();
    }

    private class Node<T> implements Comparable<T> {
        T t;
        Node<T> leftNode;
        Node<T> rightNode;

        public Node(T t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return String.valueOf(t);
        }

        @Override
        public int compareTo(T o) {
            if (t.equals(o)) {
                return 0;
            } else {
                if (o == null) {
                    return 1;
                } else {
                    return t.hashCode() > o.hashCode() ? 1 : -1;
                }
            }
        }
    }
}
