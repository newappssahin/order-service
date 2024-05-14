package com.melita.orderservice.queue;

import com.melita.orderservice.model.dto.OrderEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public void publishMessage(OrderEventDTO orderEventDTO) {
        rabbitTemplate.convertAndSend(QueueChannels.Exchange.ORDER_EXCHANGE, "", orderEventDTO);
    }

}
