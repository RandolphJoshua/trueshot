package com.ongtangco.trueshot.dto;

import com.ongtangco.trueshot.enums.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderStatusUpdateRequest {
    @NotNull
    private OrderStatus status;

    private String remarks;
}
