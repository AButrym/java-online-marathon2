package com.softserve.edu.exception;

public class ProgressNotFoundException extends EntityNotFoundException {
    public ProgressNotFoundException(Long id) {
        super(id, "progress");
    }
}
