package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID id) {
        super(id, "user");
    }
}
