package com.softserve.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Marathon")  // 404
public class MarathonNotFoundException extends EntityNotFoundException {
    public MarathonNotFoundException(Long id) {
        super(id, "marathon");
    }
}
