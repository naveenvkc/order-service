package com.polarbookshop.orderservice.model.event;

public record OrderAcceptedMessage(
        Long orderId
) { }
