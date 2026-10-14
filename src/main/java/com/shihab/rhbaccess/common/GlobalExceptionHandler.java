package com.shihab.rhbaccess.common;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalid(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Validation failed", details);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> conflict(DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT, "Data integrity violation (e.g. duplicate email)", List.of());
    }

    @ExceptionHandler(ExternalCallException.class)
    public ResponseEntity<ApiError> external(ExternalCallException ex) {
        return build(HttpStatus.BAD_GATEWAY, ex.getMessage(), List.of());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String msg, List<String> details) {
        return ResponseEntity.status(status).body(ApiError.of(status.value(), msg, details));
    }
}
