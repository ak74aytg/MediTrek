package com.airtribe.MediTrack.interface_;

import java.util.List;

public interface Searchable<T> {

    T searchById(String id);

    List<T> searchByName(String name);

    List<T> searchByEmail(String email);
}
