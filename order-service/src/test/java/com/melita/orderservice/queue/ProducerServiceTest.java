package com.melita.orderservice.queue;

import com.melita.orderservice.BaseUnitTest;
import com.melita.orderservice.model.dto.OrderEventDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProducerServiceTest extends BaseUnitTest {

    @Mock
    RabbitTemplate rabbitTemplate;

    ProducerService producerService;

    @BeforeEach
    void setUp() {
        producerService = new ProducerService(rabbitTemplate);
    }

    @Test
    void publishMessage_shouldSucceed() {
        OrderEventDTO orderEvent = getOrderEvent();

        producerService.publishMessage(orderEvent);

        verify(rabbitTemplate).convertAndSend(QueueChannels.Exchange.ORDER_EXCHANGE, "", orderEvent);
    }


}