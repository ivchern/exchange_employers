package com.ivchern.exchange_employers.Common;

import com.ivchern.exchange_employers.Common.Exception.ForbiddenException;
import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.Common.Exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object exceptionHandler(MissingServletRequestParameterException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exceptionResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object exceptionHandler(HttpRequestMethodNotSupportedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public Object exceptionHandler(ForbiddenException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public Object exceptionHandler(NotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgument.class)
    public Object exceptionHandler(IllegalArgument e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception e) {
        log.error("FATAL: e=", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage("FATAL"));
    }
}
