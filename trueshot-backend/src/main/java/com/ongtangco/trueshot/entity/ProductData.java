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

    @Column(nullable = false, name = "modelName")
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "conditionGrade")
    private ConditionGrade conditionGrade;

    @Column(length = 2000)
    private String description;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "lensMount")
    private String lensMount;

    @Column(name = "sensorType")
    private String sensorType;

    @Column(name = "releaseYear")
    private Integer releaseYear;

    @Column(name = "shutterCount")
    private Integer shutterCount;

    @Column(name = "stockQuantity")
    private Integer stockQuantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean featured;

    private Boolean available;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lastUpdated")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
