package com.deloittedigital.library.exception;

public class CategoryNotFoundException extends RestException {
    public CategoryNotFoundException() {
        super(ErrorType.CATEGORY_NOT_FOUND);
    }
}
