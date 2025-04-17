package com.polarbookshop.orderservice.operation;

import com.polarbookshop.orderservice.consumer.RestConsumerRequest;
import com.polarbookshop.orderservice.mapper.OrderMapper;
import com.polarbookshop.orderservice.model.OrderResponse;
import com.polarbookshop.orderservice.model.OrderResponseModel;
import com.polarbookshop.orderservice.persistence.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllOrdersOperation implements IOperation<RestConsumerRequest<Void>, OrderResponseModel>{
    private static final Logger LOG = LoggerFactory.getLogger(GetAllOrdersOperation.class);

    private final OrderRepository orderRepository;

    public GetAllOrdersOperation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseModel handle(RestConsumerRequest<Void> consumerRequest) {
        LOG.info("Entered GetAllBooksOperation.handle Operation");
        return prepareConsumerResponse(consumerRequest);
    }

    private OrderResponseModel prepareConsumerResponse(RestConsumerRequest<Void> consumerRequest) {
        LOG.info("Entered prepareConsumerResponse");
        List<OrderResponse> orderResponses = OrderMapper.mapEntitiesToResponse(orderRepository.findAll());
        return OrderResponseModel.builder()
                .data(orderResponses)
                .build();
    }

}
