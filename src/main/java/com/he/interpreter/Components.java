package com.he.interpreter;

import com.he.tree.Node;

import com.he.iterator.Iterator;
import java.util.function.Predicate;

public class Components {

    public static Node find(Iterator<Node> iterator, Predicate<Node> predicate) {
        while (iterator.hasNext()) {
            Node cur = iterator.next();
            if (predicate.test(cur)) {
                return cur;
            }
        }
        return null;
    }

}

