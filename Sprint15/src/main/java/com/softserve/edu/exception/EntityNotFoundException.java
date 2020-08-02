package com.softserve.edu.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entityName) {
        super("No " + entityName + " for id: " + id);
    }
}
