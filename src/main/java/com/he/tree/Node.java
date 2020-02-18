package com.he.tree;

import java.util.*;

public class Node implements Comparable<Node> {
    private String name;
    private NodeType type;
    private Integer priority;
    private Node parent;
    private List<Node> children;

    public Node(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.priority = builder.priority;
        addAllChildren(Objects.requireNonNullElse(builder.children, new ArrayList<>()));
    }

    public void addAllChildren(List<Node> children) {
        for (Node child : children) {
            addChild(child);
        }
    }

    public void addChild(Node child) {
        this.children.add(child);
        child.parent = this;
    }

    public void removeChild(Node child) {
        this.children.remove(child);
        child.parent = null;
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChild() {
        return children;
    }

    public void setChild(List<Node> child) {
        this.children = children;
    }

    public String getPath() {
        Deque<String> deque = new LinkedList<>();
        Node cur = this;
        while (cur != null) {
            deque.addFirst(cur.getName());
            cur = cur.getParent();
        }
        return String.join(",", deque);
    }

    public String toString() {
        return "GPSNode(name=" + this.getName() + ", type=" + this.getType() + ", priority=" + this.getPriority() + ", children=" + this.getChildren() + ")";
    }

    @Override
    public int compareTo(Node o) {
        return (this.priority - o.priority);
    }

    public static class Builder {
        private String   name;
        private NodeType type;
        private int        priority;
        private List<Node> children;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(NodeType type) {
            this.type = type;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder children(List<Node> children) {
            this.children = children;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }
}
