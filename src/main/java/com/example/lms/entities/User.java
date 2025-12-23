package com.example.lms.entities;

import java.util.List;
import java.util.ArrayList;

public record User(String id, String name, List<String> borrowedBookIsbns) {
    public User(String id, String name) {
        this(id, name, new ArrayList<>());
    }
}
