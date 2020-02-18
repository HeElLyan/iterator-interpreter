package com.he.iterator;

public interface Iterator<E> {
    E next();
    boolean hasNext();
    E getValue();
}
