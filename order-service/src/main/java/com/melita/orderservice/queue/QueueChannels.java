package com.melita.orderservice.queue;

public interface QueueChannels {
    interface Exchange {
        String ORDER_EXCHANGE = "order-processing";
    }
    
    interface Queue {
        String ORDER_QUEUE = Exchange.ORDER_EXCHANGE + ".status";
    }
}
