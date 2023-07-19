package com.ivchern.exchange_employers.Common;

import com.ivchern.exchange_employers.Common.Exception.ForbiddenException;
import com.ivchern.exchange_employers.Common.Exception.IllegalArgument;
import com.ivchern.exchange_employers.Common.Exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    @Autowired
    private HttpServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object exceptionHandler(MissingServletRequestParameterException e) {
        logger.error("Success error: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), LocalDate.now(),
                e.getMessage(), HttpServletResponse.SC_OK);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exceptionResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object exceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.error("Not found request: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), LocalDate.now(),
                e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public Object exceptionHandler(ForbiddenException e) {
        logger.error("Access denied: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), LocalDate.now(),
                e.getMessage(), HttpServletResponse.SC_FORBIDDEN);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public Object exceptionHandler(NotFoundException e) {
        logger.error("Not found request: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), LocalDate.now(),
                e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgument.class)
    public Object exceptionHandler(IllegalArgument e) {
        logger.error("Global error: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getRequestURI(), LocalDate.now(),
                e.getMessage(), HttpServletResponse.SC_CONFLICT);
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
