package com.ongtangco.trueshot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ongtangco.trueshot.enums.ConditionGrade;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_data")
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConditionGrade conditionGrade;

    @Column(length = 2000)
    private String description;

    private String imageUrl;

    private String lensMount;

    private String sensorType;

    private Integer releaseYear;

    private Integer shutterCount;

    private Integer stockQuantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean featured;

    private Boolean available;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
