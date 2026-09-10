package util;

import java.util.ArrayList;
import java.util.List;

public class DataStore<T> {
    private final List<T> data = new ArrayList<>();

    public void add(T item) {
        data.add(item);
    }

    public List<T> getAll() {
        // here i intentionally returned data to keep things simple
        // else i know we should not return list objects directly ,
        // as anyone can update the data objects in that case.
        return data;
    }
}
