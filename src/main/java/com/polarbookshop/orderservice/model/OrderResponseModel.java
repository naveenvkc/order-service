package com.polarbookshop.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseModel {

    @JsonProperty("data")
    private List<OrderResponse> data;

    @JsonProperty("notifications")
    private List<NotificationResponse> notifications = null;
}
