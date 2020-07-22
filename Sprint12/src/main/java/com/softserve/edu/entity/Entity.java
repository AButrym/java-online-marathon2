package com.softserve.edu.entity;

public class Entity {
    private static int counter = 1;

    private final int id = counter++; // must be unique
    private final String name;

    private Entity(String name) {
        this.name = name;
    }

    public static Entity of(String name) {
        return new Entity(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
