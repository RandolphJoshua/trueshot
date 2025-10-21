package com.ongtangco.trueshot.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AccountRequest {
    private Integer id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private String fullName;

    private String role;

    private String notes;
}
