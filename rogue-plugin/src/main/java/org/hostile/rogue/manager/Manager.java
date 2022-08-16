package org.hostile.rogue.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Manager<T> {

    private final List<T> objects = new ArrayList<>();

    public void add(T object) {
        objects.add(object);
    }

    public void addAll(List<T> objects) {
        this.objects.addAll(objects);
    }

    @SafeVarargs
    public final void addAll(T... objects) {
        Arrays.stream(objects).forEach(this::add);
    }

    public List<T> getIf(Predicate<T> predicate) {
        return objects.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<T> getAll() {
        return objects;
    }

    public void removeIf(Predicate<T> predicate) {
        objects.removeIf(predicate);
    }
}
