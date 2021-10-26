package com.deloittedigital.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailAlreadyUsedException extends RestException {
    public EmailAlreadyUsedException() {
        super(ErrorType.EMAIL_ALREADY_USED);
    }
}
