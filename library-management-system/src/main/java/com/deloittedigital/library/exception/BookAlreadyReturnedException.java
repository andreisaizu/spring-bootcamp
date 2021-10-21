package com.deloittedigital.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The book was already returned")
public class BookAlreadyReturnedException extends RuntimeException {

}
