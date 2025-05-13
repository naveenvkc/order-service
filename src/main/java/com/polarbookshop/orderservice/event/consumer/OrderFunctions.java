package com.polarbookshop.orderservice.event.consumer;

import com.polarbookshop.orderservice.common.OrderStatus;
import com.polarbookshop.orderservice.entity.OrderEntity;
import com.polarbookshop.orderservice.model.event.OrderDispatchedMessage;
import com.polarbookshop.orderservice.persistence.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class OrderFunctions {

    private static final Logger log =
            LoggerFactory.getLogger(OrderFunctions.class);

    private final OrderRepository orderRepository;

    public OrderFunctions(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Bean
    public Consumer<OrderDispatchedMessage> consumeDispatchOrder() {
        return orderDispatchedMessage -> {
            Optional<OrderEntity> orderOpt = orderRepository.findById(orderDispatchedMessage.orderId());
            orderOpt.ifPresent(order -> {
                if(!order.getStatus().equals(OrderStatus.DISPATCHED.getStatus())) {
                    OrderEntity orderEntity = buildDispatchedOrder(order);
                    OrderEntity updEntity = orderRepository.save(orderEntity);
                    log.info("The order with id {} is dispatched", updEntity.getId());
                }
            });
        };
    }

    private OrderEntity buildDispatchedOrder(OrderEntity existingOrder) {
        return new OrderEntity(
                existingOrder.getId(),
                existingOrder.getBookIsbn(),
                existingOrder.getBookName(),
                existingOrder.getBookPrice(),
                existingOrder.getQuantity(),
                OrderStatus.DISPATCHED.getStatus(),
                existingOrder.getCreatedDate(),
                existingOrder.getLastModifiedDate(),
                existingOrder.getCreatedBy(),
                existingOrder.getLastModifiedBy(),
                existingOrder.getVersion());
    }
}
