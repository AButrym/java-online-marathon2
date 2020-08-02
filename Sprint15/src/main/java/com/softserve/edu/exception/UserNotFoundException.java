package com.softserve.edu.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(id, "user");
    }
}
