package com.deloittedigital.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "There already exists an account with this email")
public class EmailAlreadyUsedException extends RuntimeException {
}
