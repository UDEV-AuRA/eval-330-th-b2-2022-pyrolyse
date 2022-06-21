package com.ipiecoles.java.eval.th330.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleENFE(EntityNotFoundException e){
        ModelAndView model = new ModelAndView("error");
        model.addObject("statut", 404);
        model.addObject("message", e.getMessage());
        return model;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIAE(IllegalArgumentException e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", 400);
        model.addObject("message", e.getMessage());
        return  model;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMATME(MethodArgumentTypeMismatchException e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", 400);
        model.addObject("message", e.getMessage());
        return model;
    }
    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handle(HttpClientErrorException.Conflict e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", 409);
        model.addObject("message", e.getMessage());
        return model;
    }

}
