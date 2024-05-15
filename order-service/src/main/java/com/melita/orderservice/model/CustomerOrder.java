package com.melita.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "customer_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String installationAddress;

    private OffsetDateTime installationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String rejectionReason;

    private boolean deleted;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;

    @PrePersist
    protected void onPrePersist() {
        this.createdDate = OffsetDateTime.now();
        this.status = Status.INITIATED;
        this.deleted = false;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.modifiedDate = OffsetDateTime.now();
    }

}
