package com.ongtangco.trueshot.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_details")
public class AppDetailsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String storeName;

    private String heroMessage;

    private String supportEmail;

    private String supportNumber;

    @Column(length = 2000)
    private String faqContent;

    @Column(length = 2000)
    private String aboutContent;
}
