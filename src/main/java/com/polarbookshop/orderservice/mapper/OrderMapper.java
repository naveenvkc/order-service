package com.polarbookshop.orderservice.mapper;

import com.polarbookshop.orderservice.common.OrderStatus;
import com.polarbookshop.orderservice.entity.OrderEntity;
import com.polarbookshop.orderservice.model.OrderResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class OrderMapper {

    private OrderMapper() {}

    public static OrderResponse mapEntityToResponse(OrderEntity entity) {
        return OrderResponse.builder()
                .id(entity.getId())
                .isbn(entity.getBookIsbn())
                .title(entity.getBookName())
                .quantity(entity.getQuantity())
                .price(entity.getBookPrice())
                .status(OrderStatus.fromValue(entity.getStatus()))
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedBy(entity.getLastModifiedBy())
                .build();
    }

    public static List<OrderResponse> mapEntitiesToResponse(Iterable<OrderEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(OrderMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }
}
