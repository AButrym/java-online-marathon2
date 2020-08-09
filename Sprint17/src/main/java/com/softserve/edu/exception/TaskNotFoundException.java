package com.softserve.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Task")  // 404
public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long id) {
        super(id, "task");
    }
}
