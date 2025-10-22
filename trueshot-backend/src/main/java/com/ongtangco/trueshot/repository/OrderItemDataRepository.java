package com.ongtangco.trueshot.repository;

import com.ongtangco.trueshot.entity.OrderItemData;
import com.ongtangco.trueshot.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDataRepository extends JpaRepository<OrderItemData, Integer> {
    List<OrderItemData> findByOrderId(Integer orderId);
    List<OrderItemData> findByOrderBuyerEmailAndStatus(String buyerEmail, OrderItemStatus status);
}
