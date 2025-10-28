package com.ongtangco.trueshot.model;

import com.ongtangco.trueshot.enums.ConditionGrade;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Integer id;
    private String sku;
    private String brand;
    private String modelName;
    private ConditionGrade conditionGrade;
    private String description;
    private String imageUrl;
    private String lensMount;
    private String sensorType;
    private Integer releaseYear;
    private Integer shutterCount;
    private Integer stockQuantity;
    private BigDecimal price;
    private Boolean featured;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
