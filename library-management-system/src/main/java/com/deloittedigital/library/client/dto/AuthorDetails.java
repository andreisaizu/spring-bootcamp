package com.deloittedigital.library.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetails {
    private Integer id;
    private String name;
    private Integer yearOfBirth;
    private Integer yearOfDeath;

}
