package com.ongtangco.trueshot.serviceimpl;

import com.ongtangco.trueshot.config.AppInfoProperties;
import com.ongtangco.trueshot.model.Order;
import com.ongtangco.trueshot.model.OrderItem;
import com.ongtangco.trueshot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;
    private final AppInfoProperties appInfoProperties;

    @Override
    public void sendOrderConfirmation(Order order) {
        if (order == null) {
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(order.getBuyerEmail());
        message.setSubject("Trueshot order confirmation #" + order.getId());
        message.setFrom(appInfoProperties.getSupportEmail());
        message.setText(buildMessage(order));
        try {
            mailSender.send(message);
            log.info("Order confirmation email queued for {}", order.getBuyerEmail());
        } catch (MailException mailException) {
            log.warn("Failed to send confirmation email, falling back to log only: {}", mailException.getMessage());
        }
    }

    private String buildMessage(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(order.getBuyerName()).append(",\n\n");
        sb.append("Thank you for shopping at ").append(appInfoProperties.getStoreName()).append("!\n");
        sb.append("Order number: ").append(order.getId()).append("\n");
        sb.append("Total amount: PHP ").append(order.getTotalAmount().setScale(2, RoundingMode.HALF_UP)).append("\n\n");
        if (!CollectionUtils.isEmpty(order.getItems())) {
            sb.append("Items:\n");
            for (OrderItem item : order.getItems()) {
                sb.append(" - ").append(item.getProductBrand()).append(" ")
                        .append(item.getProductName()).append(" x")
                        .append(item.getQuantity()).append(" (PHP ")
                        .append(item.getLineTotal().setScale(2, RoundingMode.HALF_UP)).append(")\n");
            }
            sb.append("\n");
        }
        sb.append("We will reach out via ").append(order.getBuyerEmail()).append(" or ")
                .append(order.getBuyerPhone()).append(" once your camera is ready for pickup or delivery.\n\n");
        sb.append("Best regards,\n").append(appInfoProperties.getStoreName());
        return sb.toString();
    }
}
