package com.petfood.mrp.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleListContainer<E> implements Container<E> {

    private final List<E> list = Collections.synchronizedList(new ArrayList<E>());

    @Override
    public void add(E obj) {
        list.add(obj);
    }

    @Override
    public List<E> toList() {
        return Collections.unmodifiableList(new ArrayList<E>(list));
    }

    @Override
    public int size() {
        return list.size();
    }

}
