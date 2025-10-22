package com.ongtangco.trueshot.repository;

import com.ongtangco.trueshot.entity.TransactionDetailsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionDetailsDataRepository extends JpaRepository<TransactionDetailsData, Integer> {
    Optional<TransactionDetailsData> findByOrderId(Integer orderId);
}
