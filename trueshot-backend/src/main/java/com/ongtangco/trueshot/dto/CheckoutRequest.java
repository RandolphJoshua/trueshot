package com.ongtangco.trueshot.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutRequest {
    @NotBlank
    private String buyerName;

    @Email
    @NotBlank
    private String buyerEmail;

    @NotBlank
    private String buyerPhone;

    @NotEmpty
    @Valid
    private List<CheckoutItemRequest> items = new ArrayList<>();

    private String specialInstructions;

    private String paymentProvider;

    private String paymentReference;

    private BigDecimal amountPaid;
}
