package com.softserve.edu.controller;

import com.softserve.edu.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNotFoundException(Throwable error) {
        log.warn("EntityNotFoundException: {}", error.getMessage());
        ModelAndView model = new ModelAndView("404");
        model.addObject("errorMsg", error);
        model.setStatus(HttpStatus.NOT_FOUND);
        return model;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityException(Throwable error) {
        log.warn("DataIntegrityViolationException: {}", error.getMessage());
        ModelAndView model = new ModelAndView("400");
        model.addObject("errorMsg",
                "Your data can't be added to the database." +
                        " May be such record is malformed or already exists.");
        model.setStatus(HttpStatus.BAD_REQUEST); // 400
        return model;
    }
}
