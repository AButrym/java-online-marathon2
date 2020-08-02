package com.softserve.edu.exception;

public class MarathonNotFoundException extends EntityNotFoundException {
    public MarathonNotFoundException(Long id) {
        super(id, "marathon");
    }
}
