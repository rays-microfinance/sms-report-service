package com.sahay.exception;


import com.sahay.dto.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException exceptions, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = exceptions.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponse> messageNotFoundExceptionHandler(MessageNotFoundException exception, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse( "999" , exception.getMessage());

        return ResponseEntity.status(404).body(errorResponse);


    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintValidationExceptionHandler(ConstraintViolationException exception, WebRequest request) {
        List<String> errors = new ArrayList<>();

        exception.getConstraintViolations().forEach(ex -> errors.add(ex.getMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
