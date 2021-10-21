package com.deloittedigital.library.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthorDetails {
    private Integer id;
    private String name;
    private Integer yearOfBirth;
    private Integer yearOfDeath;

}
