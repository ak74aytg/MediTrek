package com.airtribe.MediTrack.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataStore<T> {

    private List<T> data;

    public DataStore() {
        this.data = new ArrayList<>();
    }

    public void add(T item) {
        Objects.requireNonNull(item, "Item cannot be null");
        data.add(item);
    }

    public T get(int index) {
        if (index < 0 || index >= data.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + data.size());
        }
        return data.get(index);
    }

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public boolean remove(T item) {
        return data.remove(item);
    }

    public T removeAt(int index) {
        if (index < 0 || index >= data.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + data.size());
        }
        return data.remove(index);
    }

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean contains(T item) {
        return data.contains(item);
    }

    public int indexOf(T item) {
        return data.indexOf(item);
    }

    @Override
    public String toString() {
        return "DataStore{" +
               "size=" + data.size() +
               ", items=" + data +
               '}';
    }
}
