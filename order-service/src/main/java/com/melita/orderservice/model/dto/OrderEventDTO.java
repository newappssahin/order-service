package com.melita.orderservice.model.dto;

import com.melita.orderservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {

    private Long orderId;
    private Status status;
    private String rejectReason;
}
