package com.melita.orderservice.model.dto;

import com.melita.orderservice.model.Status;
import lombok.Data;

@Data
public class OrderStatusUpdateDTO {
    private Status status;
    private String rejectionReason;
}
