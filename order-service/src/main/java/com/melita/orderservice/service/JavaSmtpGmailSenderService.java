package com.melita.orderservice.service;

import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaSmtpGmailSenderService {
    private final JavaMailSender emailSender;

    public void sendEmail(CustomerOrder order) {
        String message = getMessage(order);
        sendEmail(order.getEmail(), message);
    }

    private void sendEmail(String toEmail, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1suleymansahin1@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Customer Order");
        message.setText(body);

        emailSender.send(message);

        log.info("Message sent successfully");
    }

    private static String getMessage(CustomerOrder order) {
        Product product = order.getProduct();
        return
                switch (order.getStatus()) {
                    case REJECTED:
                        yield String.format("Your order: %s with %s is rejected.", product.getName(), product.getFeature());
                    case COMPLETED:
                        yield String.format("Your order: %s with %s is completed.\n\nWe will be your address at the %s. ", product.getName(), product.getFeature(), DateUtils.format(order.getInstallationDate()));
                    case IN_PROGRESS:
                        yield String.format("Your order: %s with %s is processing", product.getName(), product.getFeature());
                    default:
                        yield "Please check your order or contact with customer service";
                };
    }
}
