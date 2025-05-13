package com.polarbookshop.orderservice.consumer;

import com.polarbookshop.orderservice.model.GetOrdersRequest;
import com.polarbookshop.orderservice.model.OrderRequest;
import com.polarbookshop.orderservice.model.OrderResponseModel;
import com.polarbookshop.orderservice.model.SubmitOrderResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${openapi.order.base-path:/api/v1}")
public class OrderController implements OrderControllerInterface{

    private final OrderRequestDelegate delegate;

    public OrderController(OrderRequestDelegate orderRequestDelegate) {
        this.delegate = orderRequestDelegate;
    }

    @Override
    public ResponseEntity<OrderResponseModel> getAllOrders(@AuthenticationPrincipal Jwt jwt){
        System.out.println("username::" + jwt.getClaimAsString("preferred_username"));
        System.out.println("sub::" + jwt.getSubject());
        GetOrdersRequest getOrderRequest =
                GetOrdersRequest.builder().username(jwt.getClaimAsString("preferred_username")).build();
        RestConsumerRequest<GetOrdersRequest> restRequest = RestConsumerRequest.<GetOrdersRequest>builder()
                                                                .request(getOrderRequest)
                                                                .build();
        return this.delegate.getAllOrders(restRequest);
    }

    @Override
    public ResponseEntity<SubmitOrderResponseModel> submitOrder(
            @Valid @RequestBody OrderRequest restConsumerRequest) {
        RestConsumerRequest<OrderRequest> consumerRequest =
                                        RestConsumerRequest.<OrderRequest>builder()
                                                .request(restConsumerRequest)
                                                .build();
        return this.delegate.submitOrder(consumerRequest);
    }
}
