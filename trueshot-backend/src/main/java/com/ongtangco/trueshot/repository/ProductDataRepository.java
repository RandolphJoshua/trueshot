package com.ongtangco.trueshot.repository;

import com.ongtangco.trueshot.entity.ProductData;
import com.ongtangco.trueshot.enums.ConditionGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDataRepository extends JpaRepository<ProductData, Integer> {

    List<ProductData> findByBrandIgnoreCase(String brand);

    @Query("SELECT p FROM ProductData p WHERE lower(p.brand) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.modelName) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.description) LIKE lower(concat('%', :keyword, '%'))")
    List<ProductData> searchByKeyword(@Param("keyword") String keyword);

    List<ProductData> findByConditionGrade(ConditionGrade conditionGrade);
}
