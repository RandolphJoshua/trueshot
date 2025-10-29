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

    @Column(nullable = false, name = "storeName")
    private String storeName;

    @Column(name = "heroMessage")
    private String heroMessage;

    @Column(name = "supportEmail")
    private String supportEmail;

    @Column(name = "supportNumber")
    private String supportNumber;

    @Column(length = 2000, name = "faqContent")
    private String faqContent;

    @Column(length = 2000, name = "aboutContent")
    private String aboutContent;
}
