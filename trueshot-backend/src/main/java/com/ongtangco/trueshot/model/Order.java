package com.ongtangco.trueshot.model;

import com.ongtangco.trueshot.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private Integer id;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String specialInstructions;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private List<OrderItem> items = new ArrayList<>();
    private TransactionDetails transactionDetails;
}
