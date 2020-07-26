package edu.softserve.jom.sprint13.exception;

import java.util.UUID;

public class MarathonNotFoundException extends EntityNotFoundException {
    public MarathonNotFoundException(UUID id) {
        super(id, "marathon");
    }
}
