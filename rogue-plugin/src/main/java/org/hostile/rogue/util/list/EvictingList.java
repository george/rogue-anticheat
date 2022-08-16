package org.hostile.rogue.util.list;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class EvictingList<T> extends ArrayList<T> {

    private final int maxSize;

    public EvictingList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T object) {
        if (size() >= maxSize) {
            remove(0);
        }

        return super.add(object);
    }
}
