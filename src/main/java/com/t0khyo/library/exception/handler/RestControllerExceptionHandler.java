package com.t0khyo.library.exception.handler;

import com.t0khyo.library.exception.BookIsAlreadyBorrowedException;
import com.t0khyo.library.exception.BookNotFoundException;
import com.t0khyo.library.exception.BorrowingRecordNotFoundException;
import com.t0khyo.library.exception.PatronNotFoundException;
import com.t0khyo.library.exception.dto.ErrorResponse;
import com.t0khyo.library.exception.dto.SubErrorResponse;
import com.t0khyo.library.exception.dto.ValidationError;
import com.t0khyo.library.exception.dto.ValidationSubError;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value={BookNotFoundException.class, PatronNotFoundException.class, BorrowingRecordNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("EntityNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BookIsAlreadyBorrowedException.class)
    public ResponseEntity<ErrorResponse> handleBookIsAlreadyBorrowedException(BookIsAlreadyBorrowedException ex) {
        log.warn("BookIsAlreadyBorrowedException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), "The book must be returned first.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("DataIntegrityViolationException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getBindingResult());

        List<SubErrorResponse> subErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationSubError.builder()
                        .object(error.getObjectName())
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build()
                )
                .collect(Collectors.toList());

        ValidationError errorResponse = ValidationError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .debugMessage(ex.getMessage())
                .subErrors(subErrors)
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("MethodArgumentTypeMismatchException: {}", ex.getMessage());
        String errorMessage = "Failed to convert parameter '" + ex.getName() + "' to type '" + ex.getRequiredType().getSimpleName() + "'";

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.toString());
    }

    // helper methods
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String debugMessage) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .debugMessage(debugMessage)
                .build();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .debugMessage("No additional debug information available.")
                .build();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}
