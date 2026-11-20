package com.covoiturage.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ProblemDetail createError(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message);

        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());

        return createError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }








}