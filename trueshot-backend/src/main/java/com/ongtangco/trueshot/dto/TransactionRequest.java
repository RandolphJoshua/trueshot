package com.ongtangco.trueshot.dto;

import com.ongtangco.trueshot.enums.PaymentStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private Integer id;

    @NotNull
    private Integer orderId;

    @NotBlank
    private String provider;

    @NotBlank
    private String transactionReference;

    @NotNull
    private PaymentStatus paymentStatus;

    private BigDecimal amountPaid;

    private LocalDateTime paidAt;

    private String remarks;
}
