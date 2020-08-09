package com.softserve.edu.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView();
        String errorMsg;
        int httpErrorCode = getErrorCode(httpRequest);

        log.error("Uncaught error with status code: {}", httpErrorCode);

        switch (httpErrorCode) {
            case 400:
                errorMsg = "Http Error Code: 400. Bad Request";
                errorPage.setViewName("400");
                break;
            case 401:
                errorMsg = "Http Error Code: 401. Unauthorized";
                errorPage.setViewName("errorPage");
                break;
            case 404:
                errorMsg = "Http Error Code: 404. Resource not found";
                errorPage.setViewName("404");
                break;
            case 500:
                errorMsg = "Http Error Code: 500. Internal Server Error";
                errorPage.setViewName("500");
                break;
            default:
                errorMsg = "Unknown error. Don't panic! We are working on it.";
                errorPage.setViewName("errorPage");
                break;
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
