package com.softserve.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Sprint")  // 404
public class SprintNotFoundException extends EntityNotFoundException {
    public SprintNotFoundException(Long id) {
        super(id, "sprint");
    }
}
