package com.ongtangco.trueshot.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account_data")
public class AccountData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    private String role;

    @Column(length = 1000)
    private String notes;
}
