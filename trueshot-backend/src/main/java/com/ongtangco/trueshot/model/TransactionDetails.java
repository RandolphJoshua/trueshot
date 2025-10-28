package com.ongtangco.trueshot.model;

import com.ongtangco.trueshot.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDetails {
    private Integer id;
    private Integer orderId;
    private String provider;
    private String transactionReference;
    private BigDecimal amountPaid;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
