package com.ld.application.handler;


import io.swagger.v3.oas.annotations.Hidden;
import ld.domain.user.exception.UserDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Hidden
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException exception,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Une erreur serveur est survenue.");
        body.put("error", exception.getMessage());
        return new ResponseEntity<>(
                body,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<Object> handleUserDomainException(
            UserDomainException exception,
            WebRequest request) {

        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("Les erreurs rencontrées",
                exception.getRuntimeExceptions().stream()
                        .map(Throwable::getMessage)
                        .map(message -> messageSource.getMessage(
                                message,
                                null,
                                message,
                                LocaleContextHolder.getLocale()
                        ))
                        .collect(Collectors.toList())
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

}
