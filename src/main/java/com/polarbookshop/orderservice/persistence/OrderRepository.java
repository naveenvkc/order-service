package com.polarbookshop.orderservice.persistence;

import com.polarbookshop.orderservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Long> {
}
