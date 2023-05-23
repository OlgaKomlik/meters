package com.meters.exceptions;

import com.meters.exceptoins.EntityIsDeletedException;
import com.meters.exceptoins.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorMessage.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityIsDeletedException.class)
    public ResponseEntity<ErrorMessage> handleEntityIsDeletedException(EntityIsDeletedException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        errorMessage.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
