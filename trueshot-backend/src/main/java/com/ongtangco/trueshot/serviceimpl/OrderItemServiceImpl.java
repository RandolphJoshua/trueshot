package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.entity.OrderItemData;
import com.ongtangco.trueshot.enums.OrderItemStatus;
import com.ongtangco.trueshot.model.OrderItem;
import com.ongtangco.trueshot.repository.OrderItemDataRepository;
import com.ongtangco.trueshot.service.OrderItemService;
import com.ongtangco.trueshot.util.Transform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDataRepository orderItemDataRepository;
    private final Transform<OrderItemData, OrderItem> transform = new Transform<>(OrderItem.class);

    @Override
    public List<OrderItem> getItemsForOrder(Integer orderId) {
        return orderItemDataRepository.findByOrderId(orderId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> getItemsForEmail(String email, OrderItemStatus status) {
        return orderItemDataRepository.findByOrderBuyerEmailAndStatus(email, Optional.ofNullable(status).orElse(OrderItemStatus.ORDERED))
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    private OrderItem toModel(OrderItemData data) {
        OrderItem item = transform.transform(data);
        if (item != null) {
            item.setOrderId(data.getOrder().getId());
            item.setProductId(data.getProduct() != null ? data.getProduct().getId() : null);
        }
        return item;
    }
}
