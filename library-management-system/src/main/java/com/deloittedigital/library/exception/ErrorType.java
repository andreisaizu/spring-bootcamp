package com.deloittedigital.library.exception;

public enum ErrorType {
    BOOK_NOT_FOUND("0001", "Book not found"),
    CATEGORY_NOT_FOUND("0002", "Category not found"),
    EMAIL_ALREADY_USED("0003", "Email already used"),
    NULL_POINTER_EXCEPTION("0000", "Internal error, retry later!");
    private String errorCode;
    private String reason;

    ErrorType(String errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getReason() {
        return this.reason;
    }
}
