package com.deloittedigital.library.model.dto;

public enum SortFieldDTO {
    TITLE ("title"),
    AUTHOR ("author");

    private final String name;

    SortFieldDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
