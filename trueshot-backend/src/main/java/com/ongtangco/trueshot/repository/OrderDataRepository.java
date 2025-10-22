package com.ongtangco.trueshot.repository;

import com.ongtangco.trueshot.entity.OrderData;
import com.ongtangco.trueshot.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDataRepository extends JpaRepository<OrderData, Integer> {
    List<OrderData> findByBuyerEmailOrderByCreatedAtDesc(String buyerEmail);
    List<OrderData> findByStatusOrderByCreatedAtDesc(OrderStatus status);
}
