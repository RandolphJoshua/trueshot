package com.ongtangco.trueshot.dto;

import com.ongtangco.trueshot.model.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {
    private Integer orderId;
    private String message;
    private Order order;
}
