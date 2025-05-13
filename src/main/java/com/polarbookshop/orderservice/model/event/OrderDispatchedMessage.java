package com.polarbookshop.orderservice.model.event;

public record OrderDispatchedMessage(
        Long orderId
) { }
