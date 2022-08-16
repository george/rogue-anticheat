package org.hostile.rogue.util.list;

import lombok.Getter;

import java.util.function.Function;

@Getter
public class RollingCollection<T extends Number, U> {

    private final EvictingList<T> list;
    private final Function<EvictingList<T>, U> providingFunction;

    public RollingCollection(int size, Function<EvictingList<T>, U> function) {
        this.list = new EvictingList<>(size);
        this.providingFunction = function;
    }

    public U add(T value) {
        list.add(value);

        return providingFunction.apply(list);
    }

    public int size() {
        return list.size();
    }
}
