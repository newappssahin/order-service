package com.melita.orderservice.queue;

import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.service.JavaSmtpGmailSenderService;
import com.melita.orderservice.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Getter
@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final OrderService orderService;
    private final JavaSmtpGmailSenderService gmailSenderService;

    @SneakyThrows
    @RabbitListener(queues = QueueChannels.Queue.ORDER_QUEUE)
    @Transactional
    public void listen(OrderEventDTO orderDTO) {
        log.info("Received OrderEvent id: {}", orderDTO.getOrderId());
        CustomerOrder order = orderService.getOrderById(orderDTO.getOrderId());

        if (Objects.equals(order.getStatus(), orderDTO.getStatus())) {
            throw new Exception(String.format("Order status already has this status: %s ", orderDTO.getStatus()));
        }

        CustomerOrder customerOrder = orderService.updateStatus(order, orderDTO);
        gmailSenderService.sendEmail(customerOrder);
    }

}
