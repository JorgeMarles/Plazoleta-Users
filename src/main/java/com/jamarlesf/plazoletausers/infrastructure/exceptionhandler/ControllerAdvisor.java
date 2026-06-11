package com.jamarlesf.plazoletausers.infrastructure.exceptionhandler;

import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import com.jamarlesf.plazoletausers.domain.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException ex) {
        return ResponseEntity.badRequest().body(Map.of(MESSAGE, ex.getMessage()));
    }

}
