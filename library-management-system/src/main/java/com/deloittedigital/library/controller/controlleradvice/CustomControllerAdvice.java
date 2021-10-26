package com.deloittedigital.library.controller.controlleradvice;

import com.deloittedigital.library.exception.BookNotFoundException;
import com.deloittedigital.library.exception.CategoryNotFoundException;
import com.deloittedigital.library.exception.EmailAlreadyUsedException;
import com.deloittedigital.library.exception.RestException;
import com.deloittedigital.library.model.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.deloittedigital.library.exception.ErrorType.NULL_POINTER_EXCEPTION;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({BookNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(RestException restException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .errorCode(restException.getErrorType().getErrorCode())
                        .reason(restException.getErrorType().getReason())
                        .build());
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleConflictException(RestException restException) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .errorCode(restException.getErrorType().getErrorCode())
                        .reason(restException.getErrorType().getReason())
                        .build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleNullPointerException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .errorCode(NULL_POINTER_EXCEPTION.getErrorCode())
                        .reason(NULL_POINTER_EXCEPTION.getReason())
                        .build());
    }


}
