package com.ongtangco.trueshot.model;

import lombok.Data;

@Data
public class Account {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private String notes;
}
