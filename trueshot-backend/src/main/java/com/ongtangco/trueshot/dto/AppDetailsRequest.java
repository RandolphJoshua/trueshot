package com.ongtangco.trueshot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppDetailsRequest {
    private Integer id;

    @NotBlank
    private String storeName;

    private String heroMessage;

    @NotBlank
    private String supportEmail;

    private String supportNumber;

    private String faqContent;

    private String aboutContent;
}
