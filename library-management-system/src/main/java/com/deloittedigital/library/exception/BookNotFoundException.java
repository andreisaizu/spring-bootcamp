package com.deloittedigital.library.exception;

public class BookNotFoundException extends RestException {
    public BookNotFoundException() {
        super(ErrorType.BOOK_NOT_FOUND);
    }
}
