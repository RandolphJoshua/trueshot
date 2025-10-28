package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.dto.CheckoutItemRequest;
import com.ongtangco.trueshot.dto.CheckoutRequest;
import com.ongtangco.trueshot.dto.OrderStatusUpdateRequest;
import com.ongtangco.trueshot.dto.TransactionRequest;
import com.ongtangco.trueshot.entity.OrderData;
import com.ongtangco.trueshot.entity.OrderItemData;
import com.ongtangco.trueshot.entity.ProductData;
import com.ongtangco.trueshot.entity.TransactionDetailsData;
import com.ongtangco.trueshot.enums.OrderItemStatus;
import com.ongtangco.trueshot.enums.OrderStatus;
import com.ongtangco.trueshot.enums.PaymentStatus;
import com.ongtangco.trueshot.model.Order;
import com.ongtangco.trueshot.model.OrderItem;
import com.ongtangco.trueshot.model.TransactionDetails;
import com.ongtangco.trueshot.repository.OrderDataRepository;
import com.ongtangco.trueshot.repository.ProductDataRepository;
import com.ongtangco.trueshot.repository.TransactionDetailsDataRepository;
import com.ongtangco.trueshot.service.NotificationService;
import com.ongtangco.trueshot.service.OrderService;
import com.ongtangco.trueshot.util.Transform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ProductDataRepository productDataRepository;
    private final OrderDataRepository orderDataRepository;
    private final TransactionDetailsDataRepository transactionDetailsDataRepository;
    private final NotificationService notificationService;

    private final Transform<OrderData, Order> orderTransform = new Transform<>(Order.class);
    private final Transform<OrderItemData, OrderItem> orderItemTransform = new Transform<>(OrderItem.class);
    private final Transform<TransactionDetailsData, TransactionDetails> transactionTransform = new Transform<>(TransactionDetails.class);

    @Override
    public Order checkout(CheckoutRequest request) {
        if (CollectionUtils.isEmpty(request.getItems())) {
            throw new IllegalArgumentException("Checkout request must contain at least one item");
        }

        OrderData orderData = new OrderData();
        orderData.setBuyerName(request.getBuyerName());
        orderData.setBuyerEmail(request.getBuyerEmail().toLowerCase(Locale.ROOT));
        orderData.setBuyerPhone(request.getBuyerPhone());
        orderData.setStatus(OrderStatus.CREATED);
        orderData.setSpecialInstructions(request.getSpecialInstructions());

        List<OrderItemData> orderItems = new ArrayList<>();
        BigDecimal runningTotal = BigDecimal.ZERO;

        for (CheckoutItemRequest itemRequest : request.getItems()) {
            ProductData product = productDataRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemRequest.getProductId()));

            OrderItemData item = new OrderItemData();
            item.setOrder(orderData);
            item.setProduct(product);
            item.setProductName(product.getModelName());
            item.setProductBrand(product.getBrand());
            item.setProductCondition(product.getConditionGrade().name());
            item.setProductImageUrl(product.getImageUrl());
            item.setUnitPrice(product.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            item.setLineTotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            item.setStatus(OrderItemStatus.ORDERED);
            orderItems.add(item);
            runningTotal = runningTotal.add(item.getLineTotal());
        }

        orderData.setItems(orderItems);
        orderData.setTotalAmount(runningTotal);

        OrderData savedOrder = orderDataRepository.save(orderData);

        if (request.getPaymentProvider() != null || request.getPaymentReference() != null || request.getAmountPaid() != null) {
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setOrderId(savedOrder.getId());
            transactionRequest.setProvider(Optional.ofNullable(request.getPaymentProvider()).orElse("MANUAL"));
            transactionRequest.setTransactionReference(Optional.ofNullable(request.getPaymentReference()).orElse("PENDING"));
            transactionRequest.setAmountPaid(request.getAmountPaid());
            transactionRequest.setPaymentStatus(request.getAmountPaid() != null && request.getAmountPaid().compareTo(BigDecimal.ZERO) > 0
                    ? PaymentStatus.PAID : PaymentStatus.PENDING);
            transactionRequest.setPaidAt(LocalDateTime.now());
            recordTransaction(transactionRequest);
            savedOrder.setStatus(PaymentStatus.PAID.equals(transactionRequest.getPaymentStatus()) ? OrderStatus.PAID : OrderStatus.PENDING_PAYMENT);
        } else {
            savedOrder.setStatus(OrderStatus.PENDING_PAYMENT);
        }

        OrderData updatedOrder = orderDataRepository.save(savedOrder);
        Order model = toModel(updatedOrder);
        notificationService.sendOrderConfirmation(model);
        return model;
    }

    @Override
    public List<Order> getAll() {
        return orderDataRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public Order getById(Integer id) {
        return orderDataRepository.findById(id).map(this::toModel).orElse(null);
    }

    @Override
    public List<Order> getByEmail(String email) {
        return orderDataRepository.findByBuyerEmailOrderByCreatedAtDesc(email.toLowerCase(Locale.ROOT))
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public Order updateStatus(Integer id, OrderStatusUpdateRequest request) {
        return orderDataRepository.findById(id).map(order -> {
            order.setStatus(request.getStatus());
            OrderData saved = orderDataRepository.save(order);
            return toModel(saved);
        }).orElse(null);
    }

    @Override
    public TransactionDetails recordTransaction(TransactionRequest request) {
        OrderData order = orderDataRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + request.getOrderId()));

        TransactionDetailsData transaction = transactionDetailsDataRepository.findByOrderId(order.getId())
                .orElseGet(TransactionDetailsData::new);
        transaction.setOrder(order);
        transaction.setProvider(request.getProvider());
        transaction.setTransactionReference(request.getTransactionReference());
        transaction.setPaymentStatus(Optional.ofNullable(request.getPaymentStatus()).orElse(PaymentStatus.PENDING));
        transaction.setAmountPaid(request.getAmountPaid());
        transaction.setPaidAt(Optional.ofNullable(request.getPaidAt()).orElse(LocalDateTime.now()));
        transaction.setRemarks(request.getRemarks());

        TransactionDetailsData saved = transactionDetailsDataRepository.save(transaction);

        if (PaymentStatus.PAID.equals(saved.getPaymentStatus())) {
            order.setStatus(OrderStatus.PAID);
            orderDataRepository.save(order);
        }

        return toTransactionModel(saved);
    }

    private Order toModel(OrderData data) {
        Order order = orderTransform.transform(data);
        if (order == null) {
            return null;
        }
        List<OrderItem> items = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data.getItems())) {
            for (OrderItemData itemData : data.getItems()) {
                OrderItem orderItem = orderItemTransform.transform(itemData);
                if (orderItem != null) {
                    orderItem.setOrderId(data.getId());
                    orderItem.setProductId(Optional.ofNullable(itemData.getProduct()).map(ProductData::getId).orElse(null));
                    items.add(orderItem);
                }
            }
        }
        order.setItems(items);
        order.setTransactionDetails(toTransactionModel(data.getTransactionDetails()));
        return order;
    }

    private TransactionDetails toTransactionModel(TransactionDetailsData data) {
        if (data == null) {
            return null;
        }
        TransactionDetails transaction = transactionTransform.transform(data);
        if (transaction != null) {
            transaction.setOrderId(Optional.ofNullable(data.getOrder()).map(OrderData::getId).orElse(null));
        }
        return transaction;
    }
}
