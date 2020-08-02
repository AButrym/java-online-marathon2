package com.softserve.edu.exception;

public class SprintNotFoundException extends EntityNotFoundException {
    public SprintNotFoundException(Long id) {
        super(id, "sprint");
    }
}
