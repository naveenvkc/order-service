package com.polarbookshop.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOrderResponseModel {

    @JsonProperty("data")
    private OrderResponse data;

    @JsonProperty("notifications")
    private List<NotificationResponse> notifications = null;
}
