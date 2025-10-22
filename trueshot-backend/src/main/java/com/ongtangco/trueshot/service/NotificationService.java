package com.ongtangco.trueshot.service;

import com.ongtangco.trueshot.model.Order;

public interface NotificationService {
    void sendOrderConfirmation(Order order);
}
