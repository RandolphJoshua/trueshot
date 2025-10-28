package com.ongtangco.trueshot.model;

import com.ongtangco.trueshot.enums.OrderItemStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private String productBrand;
    private String productCondition;
    private String productImageUrl;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal lineTotal;
    private OrderItemStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
