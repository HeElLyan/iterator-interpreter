package com.he.interpreter;

import com.he.service.TreeConverter;
import com.he.tree.Node;
import com.he.tree.NodeType;
import com.he.tree.Tree;

import java.util.Objects;

public class CrudData {

    private void add(Context context, String[] args) {

        String parentPath = args[0];
        String newName = args[1];
        String newType = args[2];
        String newPriority = args[3];

        Node newNode = new Node.Builder()
                .name(newName)
                .type(NodeType.valueOf((newType.toUpperCase())))
                .priority(Integer.parseInt(newPriority))
                .build();

        Tree tree = context.getAttribute(Context.TREE, Tree.class);
        Node foundParent = Components.find(tree.iterator(), node -> node.getPath().endsWith(parentPath));
        Objects.requireNonNull(foundParent, "Parent is not found");
        foundParent.addChild(newNode);
    }

    private void save(Context context, String[] args) {
        TreeConverter converter = context.getAttribute(Context.CONVERTER, TreeConverter.class);
        String outputFilename = context.getAttribute(Context.OUTPUT_FILENAME, String.class);
        Tree tree = context.getAttribute(Context.TREE, Tree.class);
        converter.writeTree(outputFilename, tree);
    }

    private void delete(Context context, String[] args) {

        String path = args[0];
        Tree tree = context.getAttribute(Context.TREE, Tree.class);

        Node required = Components.find(tree.iterator(), node -> node.getPath().endsWith(path));
        Objects.requireNonNull(required, "Node is not found");
        Node parent = required.getParent();
        parent.removeChild(required);
    }

}
