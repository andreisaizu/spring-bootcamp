package com.deloittedigital.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The ISBN provided is not valid. Please insert a valid ISBN")
public class InvalidISBNException extends RuntimeException {
}
