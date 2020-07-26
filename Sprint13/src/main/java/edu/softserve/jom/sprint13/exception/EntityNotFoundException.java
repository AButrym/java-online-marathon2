package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(UUID id, String entityName) {
        super("No " + entityName + " for id: " + id);
    }
}
