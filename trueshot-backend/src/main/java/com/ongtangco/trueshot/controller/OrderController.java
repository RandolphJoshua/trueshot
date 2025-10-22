package com.ongtangco.trueshot.controller;

import com.ongtangco.trueshot.dto.CheckoutRequest;
import com.ongtangco.trueshot.dto.OrderStatusUpdateRequest;
import com.ongtangco.trueshot.dto.TransactionRequest;
import com.ongtangco.trueshot.model.Order;
import com.ongtangco.trueshot.model.TransactionDetails;
import com.ongtangco.trueshot.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@Valid @RequestBody CheckoutRequest request) {
        try {
            Order order = orderService.checkout(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestParam(value = "email", required = false) String email) {
        if (email != null) {
            return ResponseEntity.ok(orderService.getByEmail(email));
        }
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Integer id,
                                              @Valid @RequestBody OrderStatusUpdateRequest request) {
        try {
            Order order = orderService.updateStatus(id, request);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/transaction")
    public ResponseEntity<TransactionDetails> recordTransaction(@PathVariable Integer id,
                                                                @Valid @RequestBody TransactionRequest request) {
        try {
            request.setOrderId(id);
            TransactionDetails details = orderService.recordTransaction(request);
            return ResponseEntity.ok(details);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
