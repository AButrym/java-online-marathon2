package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(UUID id) {
        super(id, "task");
    }
}
