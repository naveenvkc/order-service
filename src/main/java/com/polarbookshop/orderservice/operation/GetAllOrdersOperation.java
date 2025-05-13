package com.polarbookshop.orderservice.operation;

import com.polarbookshop.orderservice.consumer.RestConsumerRequest;
import com.polarbookshop.orderservice.mapper.OrderMapper;
import com.polarbookshop.orderservice.model.GetOrdersRequest;
import com.polarbookshop.orderservice.model.OrderResponse;
import com.polarbookshop.orderservice.model.OrderResponseModel;
import com.polarbookshop.orderservice.persistence.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetAllOrdersOperation implements IOperation<RestConsumerRequest<GetOrdersRequest>, OrderResponseModel>{
    private static final Logger LOG = LoggerFactory.getLogger(GetAllOrdersOperation.class);

    private final OrderRepository orderRepository;

    public GetAllOrdersOperation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseModel handle(RestConsumerRequest<GetOrdersRequest> consumerRequest) {

        LOG.info("Entered GetAllBooksOperation.handle Operation");
        return prepareConsumerResponse(consumerRequest);
    }

    private OrderResponseModel prepareConsumerResponse(RestConsumerRequest<GetOrdersRequest> consumerRequest) {
        LOG.info("Entered prepareConsumerResponse");
        GetOrdersRequest getOrdersRequest = consumerRequest.getRequest();
        List<OrderResponse> orderResponses =
                OrderMapper.mapEntitiesToResponse(orderRepository.findAllByCreatedBy(getOrdersRequest.getUsername()));
        return OrderResponseModel.builder()
                .data(orderResponses)
                .build();
    }

}
