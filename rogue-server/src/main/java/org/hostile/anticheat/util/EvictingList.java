package org.hostile.anticheat.util;

import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;

public final class EvictingList<T> extends ConcurrentLinkedQueue<T> {

    @Getter
    private final int maxSize;

    public EvictingList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(final T t) {
        if (size() >= getMaxSize()) {
            remove();
        }

        return super.add(t);
    }
}