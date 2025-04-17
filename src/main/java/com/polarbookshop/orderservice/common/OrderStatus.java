package com.polarbookshop.orderservice.common;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ACCEPTED("ACCEPTED") ,
    REJECTED("REJECTED"),
    PENDING("PENDING"),
    DISPATCHED("DISPATCHED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static OrderStatus fromValue(String value) {
        for (OrderStatus b : OrderStatus.values()) {
            if (b.status.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException(
                "Exception thrown due to unexpected value '" + value + "'");
    }
}
