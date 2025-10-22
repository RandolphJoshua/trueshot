package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.dto.CheckoutRequest;
import com.ongtangco.trueshot.dto.OrderStatusUpdateRequest;
import com.ongtangco.trueshot.dto.TransactionRequest;
import com.ongtangco.trueshot.model.Order;
import com.ongtangco.trueshot.model.TransactionDetails;

import java.util.List;

public interface OrderService {
    Order checkout(CheckoutRequest request);
    List<Order> getAll();
    Order getById(Integer id);
    List<Order> getByEmail(String email);
    Order updateStatus(Integer id, OrderStatusUpdateRequest request);
    TransactionDetails recordTransaction(TransactionRequest request);
}
