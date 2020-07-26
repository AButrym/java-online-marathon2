package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class SprintNotFoundException extends EntityNotFoundException {
    public SprintNotFoundException(UUID id) {
        super(id, "sprint");
    }
}
