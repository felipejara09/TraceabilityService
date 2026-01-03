package com.pragma.powerup.infrastructure.out.mongo.document;


import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "order_trace_logs")
public class TraceLogDocument {
    @Id
    private String id;

    private Long orderId;
    private Long clientId;
    private Long restaurantId;

    private String previousStatus;
    private String newStatus;

    private LocalDateTime changedAt;

    private Long changedByUserId;
    private String changedByRole;
}
