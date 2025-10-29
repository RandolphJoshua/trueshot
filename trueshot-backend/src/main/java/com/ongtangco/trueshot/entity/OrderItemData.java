package com.ongtangco.trueshot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ongtangco.trueshot.enums.OrderItemStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_item_data")
public class OrderItemData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_order"))
    @JsonBackReference
    private OrderData order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_product"))
    private ProductData product;

    @Column(nullable = false, name = "productName")
    private String productName;

    @Column(name = "productBrand")
    private String productBrand;

    @Column(name = "productCondition")
    private String productCondition;

    @Column(name = "productImageUrl")
    private String productImageUrl;

    @Column(nullable = false, precision = 10, scale = 2, name = "unitPrice")
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2, name = "lineTotal")
    private BigDecimal lineTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderItemStatus status;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lastUpdated")
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
