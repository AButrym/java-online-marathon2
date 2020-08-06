package com.softserve.edu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandlerException {
    private final Logger logger = LoggerFactory.getLogger(HandlerException.class);

    @Autowired(required = false)

    @ExceptionHandler({EntityNotFoundException.class, MarathonNotFoundException.class, UserNotFoundException.class, ProgressNotFoundException.class, DataIntegrityViolationException.class} )
    public ModelAndView handleMyCustomException(Throwable error){
        if (error instanceof DataIntegrityViolationException){
            logger.error("DataIntegrityViolationException");
            ModelAndView model = new ModelAndView("404");
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }
        else{
            logger.error(error.getMessage());
            ModelAndView model = new ModelAndView("error_page");
            model.addObject("info", error);
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }

    }

}
