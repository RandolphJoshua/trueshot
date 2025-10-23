package com.ongtangco.trueshot.controller;

import com.ongtangco.trueshot.enums.OrderItemStatus;
import com.ongtangco.trueshot.model.OrderItem;
import com.ongtangco.trueshot.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsForOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderItemService.getItemsForOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getItemsForEmail(@RequestParam String email,
                                                            @RequestParam(required = false) OrderItemStatus status) {
        return ResponseEntity.ok(orderItemService.getItemsForEmail(email, status));
    }
}
