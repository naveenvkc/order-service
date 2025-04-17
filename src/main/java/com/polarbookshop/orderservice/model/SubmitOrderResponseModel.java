package com.polarbookshop.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class SubmitOrderResponseModel {

    @JsonProperty("data")
    private final OrderResponse data;

    @JsonProperty("notifications")
    private final List<NotificationResponse> notifications = null;
}
