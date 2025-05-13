package com.polarbookshop.orderservice.persistence;

import com.polarbookshop.orderservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity,Long> {
    List<OrderEntity> findAllByCreatedBy(String userId);
}
