package com.softserve.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Entity")  // 404
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entityName) {
        super("No " + entityName + " for id: " + id);
    }

}
