package com.softserve.edu.exception;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long id) {
        super(id, "task");
    }
}
