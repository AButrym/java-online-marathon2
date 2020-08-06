package com.softserve.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Progress")  // 404
public class ProgressNotFoundException extends EntityNotFoundException {
    public ProgressNotFoundException(Long id) {
        super(id, "progress");
    }
}
