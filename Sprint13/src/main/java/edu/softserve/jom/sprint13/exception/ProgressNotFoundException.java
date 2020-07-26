package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class ProgressNotFoundException extends EntityNotFoundException {
    public ProgressNotFoundException(UUID id) {
        super(id, "progress");
    }
}
