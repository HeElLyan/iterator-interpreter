package com.he.iterator;

import com.he.tree.Node;

import java.util.ArrayDeque;


public class IteratorBFS implements Iterator<Node> {

    private ArrayDeque<Node> lookedTree = new ArrayDeque<>();
    private Node currentNode;

    public IteratorBFS(Node root, Node currentNode) {

        lookedTree.addLast(root);
        while (true)
            if (next() == currentNode)
                break;
    }

    @Override
    public Node next() {
        Node someNode = lookedTree.pollFirst();
        if (someNode.getChild() != null)
            for (int i = 0; i < someNode.getChild().size(); i++) {
                lookedTree.addLast(someNode.getChild().get(i));
            }
        currentNode = lookedTree.peekFirst();
        return someNode;
    }

    @Override
    public boolean hasNext() {
        Node someNode = lookedTree.pollFirst();
        if (lookedTree.peekFirst() != null) {
            lookedTree.addFirst(someNode);
            return true;
        } else {
            lookedTree.addFirst(someNode);
            return false;
        }
    }

    @Override
    public Node getValue() {
        return currentNode;
    }

}
