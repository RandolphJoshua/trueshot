package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.enums.OrderItemStatus;
import com.ongtangco.trueshot.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getItemsForOrder(Integer orderId);
    List<OrderItem> getItemsForEmail(String email, OrderItemStatus status);
}
