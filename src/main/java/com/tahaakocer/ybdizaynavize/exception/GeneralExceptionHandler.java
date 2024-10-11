package com.tahaakocer.ybdizaynavize.exception;

import com.tahaakocer.ybdizaynavize.exception.product.S3UploadImageException;
import com.tahaakocer.ybdizaynavize.service.product.S3DeleteImageException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GeneralExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex,HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(S3UploadImageException.class)
    public ResponseEntity<ErrorResponse> handleS3UploadImageException(S3UploadImageException ex,HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .error("S3 Upload Image Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(S3DeleteImageException.class)
    public ResponseEntity<ErrorResponse> handleS3DeleteImageException(S3DeleteImageException ex,HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .error("S3 Delete Image Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //========================VALIDATION EXCEPTIONS========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        String errorMessage = errors.values().stream().findFirst().orElse("Validation error");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build();

        log.error(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getConstraintViolations().forEach(constraintViolation ->
                errorMessage.append(constraintViolation.getMessage()).append("; ")
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violation Error")
                .message(errorMessage.toString())
                .path(request.getRequestURI())
                .build();

        log.error(errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        String errorMessage = "Unique constraint violation: The value for a unique field already exists.";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT.value())
                .error("Data Integrity Violation Error")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build();

        log.error("Data integrity violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
